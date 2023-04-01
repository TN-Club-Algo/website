package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {

    @GetMapping("/stat")
    fun stat(): String {
        return "admin/stat"
    }

    @GetMapping("/createContest")
    fun createContest(): String {
        return "admin/createContest"
    }

    @GetMapping("/modifyContest")
    fun modifyContest(): String {
        return "admin/editContest"
    }

    @GetMapping("/modoAdmin")
    fun modoAdmin(): String {
        return "admin/modoAdmin"
    }

    @GetMapping("/admin/answerIssue")
    fun answerIssue(): String {
        return "admin/answerIssue"
    }
}