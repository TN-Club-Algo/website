package org.algotn.website.controllers

import org.algotn.api.submission.Submission
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID
@Controller
class SubmissionController {

    @GetMapping("/submit")
    fun seeform(): String{
        return "submit"
    }
    @PostMapping("/submit")
    fun sendform(@ModelAttribute submission: Submission,
                 @RequestParam allParams: Map<String, String>): String {
        println("yeah")
        for (x in allParams.keys) {
            println("${x}: ${allParams[x]}");
        }
        return "/submit";
    }
}