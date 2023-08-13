package org.algotn.website.controllers

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.algotn.api.Chili
import org.algotn.website.auth.UserRepository
import org.algotn.website.data.TestData
import org.algotn.website.services.tests.TestLocationService
import org.redisson.client.codec.StringCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Controller
class SubmissionController {

    @Autowired
    val userRepository: UserRepository? = null

    @Autowired
    val testLocationService: TestLocationService? = null

    private val stringCodec = StringCodec()
    private val gson = Gson()

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
        @RequestParam("files") files: ArrayList<MultipartFile>
    ): Map<String, Any> {
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

        // The rest can be done in the background
        GlobalScope.launch {
            val testData = Chili.getRedisInterface().getData(username, TestData::class.java)!!
            testData.testsIds.add(testUUID)

            var name = testUUID
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

            Chili.getRedisInterface().client.getMap<String, String>("test-to-user")[testUUID] = username

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