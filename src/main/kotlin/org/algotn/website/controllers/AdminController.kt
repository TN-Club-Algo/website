package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class AdminController {

    @RequestMapping("/stat")
    fun stat(): String {
        return "admin/stat"
    }

    @RequestMapping("/createContest")
    fun createContest(): String {
        return "admin/createContest"
    }

    @RequestMapping("/modifyContest")
    fun modifyContest(): String {
        return "admin/modifyContest"
    }

    @RequestMapping("/modoAdmin")
    fun modoAdmin(): String {
        return "admin/modoAdmin"
    }

    @RequestMapping("/admin/answerIssue")
    fun answerIssue(): String {
        return "admin/answerIssue"
    }
}