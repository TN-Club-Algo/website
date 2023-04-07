package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ExamController {

    @GetMapping("/exam")
    fun examIndex(): String {
        return "exam/examIndex"
    }

    @GetMapping("/exam/submit")
    fun submit(): String {
        return "exam/examSubmit"
    }

    @GetMapping("/exam/createIssueExam")
    fun issue(): String {
        return "exam/createIssueExam"
    }

    @GetMapping("/exam/{examNumber}")
    fun exam(): String {
        return "exam/exam"
    }

    @GetMapping("/exam/{examNumber}/{problemNumber}")
    fun problem(): String {
        return "exam/problem"
    }
}