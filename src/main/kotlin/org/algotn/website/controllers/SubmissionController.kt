package org.algotn.website.controllers

import org.algotn.api.submission.Submission
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.util.UUID
@Controller
class SubmissionController {

    @GetMapping("/submit")
    fun seeform(): String{
        return "submit"
    }
    @PostMapping("/submit")
    fun sendform(@RequestParam("lang") lang: String,
                 @RequestParam("prog") prog: String,
                 @RequestParam("files") file: MultipartFile): String {
        println("yeah");
        println(lang);
        if (file.isEmpty){
            println(prog)
        }else{
            print(file.inputStream.readBytes().decodeToString())
        }
        return "/submit";
    }
}