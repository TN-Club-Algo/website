package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ProfController {

    @RequestMapping("/statExam")
    fun statExam(): String {
        return "prof/statExam"
    }

    @RequestMapping("/createExam")
    fun createExam(): String {
        return "prof/createExam"
    }

    @RequestMapping("/modifyExam")
    fun modifyExam(): String {
        return "prof/modifyExam"
    }

    @RequestMapping("/answerIssueExam")
    fun answerIssueExam(): String {
        return "prof/answerIssueExam"
    }
}