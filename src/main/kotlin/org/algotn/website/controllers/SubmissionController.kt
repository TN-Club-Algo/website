package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.redisson.client.codec.StringCodec
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Controller
class SubmissionController {

    @GetMapping("/submit/{problemId}")
    fun seeForm(@PathVariable problemId: String, model: Model): String {
        model.addAttribute("problemId", problemId)
        return "submit"//+problemId.toString()
    }

    @PostMapping("/submit/{problemId}")
    fun sendForm(
        @PathVariable("problemId") problemId: String,
        @RequestParam("lang") lang: String,
        @RequestParam("prog") prog: String,
        @RequestParam("files") files: ArrayList<MultipartFile>
    ): String {
        var name = "program"
        if (lang == "python3") name += ".py"
        var file: File? = null
        val testUUID = UUID.randomUUID().toString()
        val folder = File("tests/$testUUID")
        folder.mkdirs()
        if (files.size == 0) {
            val fileCopy = File("tests/$testUUID/$name");
            fileCopy.createNewFile()
            fileCopy.writeText(prog)

            file = fileCopy
        } else {
            name = files[0].originalFilename!!
            files.stream().forEach { fl ->
                val fileCopy = File("tests/$testUUID/${fl.originalFilename}");
                if (file == null) file = fileCopy
                fileCopy.createNewFile()
                fileCopy.writeText(fl.inputStream.readBytes().decodeToString())
            }
        }

        val problem = ProblemController.problems[problemId]
        val jsonMap = mutableMapOf<String, Any>()
        jsonMap["id"] = UUID.randomUUID().toString()
        jsonMap["programLocation"] = folder.absolutePath
        jsonMap["userProgram"] = name
        jsonMap["testType"] = "input/output"
        jsonMap["testCount"] = problem!!.tests[0].input.size

        val insideMap = mutableMapOf<String, Any>()
        insideMap["input"] = problem.tests[0].input
        insideMap["output"] = problem.tests[0].output

        jsonMap["tests"] = insideMap
        val json = Gson().toJson(jsonMap).toString()
        Chili.getRedisInterface().client.getTopic("pepper-tests", StringCodec()).publish(json)

        return "/submit";
    }
}