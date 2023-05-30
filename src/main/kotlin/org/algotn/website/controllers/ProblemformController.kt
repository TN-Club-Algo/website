package org.algotn.website.controllers

import org.algotn.api.Chili
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.util.*
import kotlin.collections.ArrayList

@Controller
class ProblemformController {

    @GetMapping("/new_problem")
    fun seeform(): String{
        return "new_problem"
    }
    @PostMapping("/new_problem")
    fun sendform(
        @RequestParam("prog") prog: String): String {
        println("yeah");
//            println(files.originalFilename)
//            print(files.inputStream.readBytes().decodeToString())

        return "/new_problem";
    }
}