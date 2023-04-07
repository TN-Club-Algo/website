package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProfController {

    @GetMapping("/statExam")
    fun statExam(): String {
        return "prof/statExam"
    }

    @GetMapping("/createExam")
    fun createExam(): String {
        return "prof/createExam"
    }

    @GetMapping("/modifyExam")
    fun modifyExam(): String {
        return "prof/editExam"
    }

    @GetMapping("/answerIssueExam")
    fun answerIssueExam(): String {
        return "prof/answerIssueExam"
    }
}