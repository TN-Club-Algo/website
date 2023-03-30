package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ExamController {

    @RequestMapping("/exam")
    fun examIndex(): String {
        return "exam/examIndex"
    }

    @RequestMapping("/exam/submit")
    fun submit(): String {
        return "exam/examSubmit"
    }

    @RequestMapping("/exam/createIssueExam")
    fun issue(): String {
        return "exam/createIssueExam"
    }

    @RequestMapping("/exam/{examNumber}")
    fun exam(): String {
        return "exam/exam"
    }

    @RequestMapping("/exam/{examNumber}/{problemNumber}")
    fun problem(): String {
        return "exam/problem"
    }
}