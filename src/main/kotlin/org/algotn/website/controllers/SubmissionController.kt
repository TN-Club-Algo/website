package org.algotn.website.controllers

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.algotn.api.Chili
import org.algotn.website.api.TestJSON
import org.algotn.website.auth.UserRepository
import org.algotn.website.data.TestData
import org.algotn.website.services.tests.TestLocationService
import org.algotn.website.utils.IPUtils
import org.redisson.client.codec.StringCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*


@Controller
class SubmissionController {

    @Autowired
    val userRepository: UserRepository? = null

    @Autowired
    val testLocationService: TestLocationService? = null

    private val stringCodec = StringCodec()
    private val gson = Gson()

    @MessageMapping("/return/tests") // Listen to /app/return/tests
    @SendTo("/return/tests") // Send to /return/tests
    fun sendTest(
        message: TestJSON,
        accessor: SimpMessageHeaderAccessor,
        @RequestParam("token") token: String
    ): TestJSON {
        println("Received test result: $message")

        // Retrieve the value of the Authorization header
        val authorizationHeader = accessor.getFirstNativeHeader("Authorization")

        println("Authorization header: $authorizationHeader")

        val isApp = accessor.user == null
        if (isApp && authorizationHeader == "token") {
            return message;
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/submit/{problemSlug}")
    fun viewSubmit(@PathVariable problemSlug: String, model: Model): String {
        val problem = Chili.getProblems().getProblem(problemSlug) ?: return "redirect:/problem"

        model.addAttribute("problem", problem)
        return "submit"
    }

    @PostMapping("/submit/{problemSlug}")
    @ResponseBody
    fun submitTest(
        @PathVariable("problemSlug") problemSlug: String,
        @RequestParam("language") lang: String,
        @RequestParam("code") prog: String,
        @RequestParam("files") files: ArrayList<MultipartFile>,
        @RequestParam("cf-turnstile-response") token: String
    ): Map<String, Any> {
        /* BEGIN CLOUDFLARE CHECK */
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = requestAttributes.request
        val ip = IPUtils.getClientIP(request)

        println("IP: $ip - Token: $token")

        val restTemplate = RestTemplate()
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("secret", "0x4AAAAAAAIoSE7qkZhMWusd6ot2RLTIpKc")
        formData.add("response", token)
        formData.add("remoteip", ip)

        val url = "https://challenges.cloudflare.com/turnstile/v0/siteverify"
        val result = restTemplate.postForEntity(url, formData, Map::class.java)

        val outcome = result.body as Map<String, Any>
        println("Cloudflare response: $outcome")
        if (!(outcome["success"] as Boolean)) {
            // CAPTCHA verification failed
            return mapOf("message" to "CAPTCHA verification failed")
        }
        /* END CLOUDFLARE CHECK */

        val principal = SecurityContextHolder.getContext().authentication.principal

        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        val testUUID = UUID.randomUUID().toString()

        val user = userRepository!!.findByUsername(username)
        if (!user.isPresent) {
            return mapOf("error" to "Authentication error", "success" to false)
        }

        val problem = Chili.getProblems().getProblem(problemSlug) ?: return mapOf(
            "error" to "Problem not found",
            "success" to false
        )

        var name = testUUID

        if (files.size == 0) {
            // No file, use the program input
            testLocationService!!.save(prog.toByteArray(), testUUID)
        } else {
            if (files.size == 1) {
                testLocationService!!.save(files[0].inputStream.readBytes(), testUUID)
            } else {
                // TODO: multiple files, maybe zip it
            }
        }

        // The rest can be done in the background
        GlobalScope.launch {
            val testData = Chili.getRedisInterface().getData(username, TestData::class.java)!!
            testData.testsIds.add(testUUID)

            Chili.getRedisInterface().client.getMap<String, String>("test-to-user")[testUUID] = username


            var extension = ""
            when (lang) {
                "python" -> {
                    extension = ".py"
                }

                "kotlin" -> {
                    extension = ".kt"
                }

                "c_cpp" -> {
                    extension = ".cpp"
                }

                "java" -> {
                    extension = ".java"
                }

                "c" -> {
                    extension = ".c"
                }
            }
            name += extension

            val jsonMap = mutableMapOf<String, Any>()
            jsonMap["problemSlug"] = problem.slug
            jsonMap["id"] = testUUID
            jsonMap["infoURL"] = "/api/tests/restricted/${problem.slug}/info"
            jsonMap["programURL"] = "/api/tests/restricted/$testUUID"
            jsonMap["userProgram"] = name
            jsonMap["language"] = lang
            jsonMap["extension"] = extension

            val json = gson.toJson(jsonMap).toString()
            Chili.getRedisInterface().client.getTopic("pepper-tests", stringCodec).publish(json)
        }

        return mapOf("redirectURL" to "/profile/test", "success" to true)
    }
}