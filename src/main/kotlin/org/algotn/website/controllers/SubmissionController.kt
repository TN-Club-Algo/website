package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.website.auth.UserRepository
import org.algotn.website.data.TestData
import org.algotn.website.services.tests.TestLocationService
import org.redisson.client.codec.StringCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @PostMapping("/submit/{problemId}")
    fun submitTest(
        principal: Principal,
        @PathVariable("problemId") problemId: String,
        @RequestParam("lang") lang: String,
        @RequestParam("prog") prog: String,
        @RequestParam("files") files: ArrayList<MultipartFile>
    ): String {
        val testUUID = UUID.randomUUID().toString()

        val user = userRepository!!.findByUsername(principal.name)
        if (!user.isPresent) {
            return "redirect:/login"
        }

        val problem = Chili.getProblems().getProblem(problemId) ?: return "redirect:/problem"

        val testData = Chili.getRedisInterface().getData(principal.name, TestData::class.java)!!
        testData.testsIds.add(testUUID)

        var name = "program"
        when (lang) {
            "python" -> {
                name += ".py"
            }

            "kotlin" -> {
                name += ".kt"
            }

            "c_cpp" -> {
                name += ".cpp"
            }

            "java" -> {
                name += ".java"
            }
        }

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

        val jsonMap = mutableMapOf<String, Any>()
        jsonMap["problemName"] = problem.slug
        jsonMap["id"] = testUUID
        jsonMap["infoURL"] = "/api/tests/restricted/${problem.slug}/info"
        jsonMap["programURL"] = "/api/tests/restricted/$testUUID"
        jsonMap["userProgram"] = name

        val json = gson.toJson(jsonMap).toString()
        Chili.getRedisInterface().client.getTopic("pepper-tests", stringCodec).publish(json)

        return "redirect:/profile/test";
    }
}