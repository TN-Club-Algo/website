package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ContestController {

    @RequestMapping("/contest")
    fun contestIndex(): String {
        return "contest/contestIndex"
    }

    @RequestMapping("/contest/submit")
    fun submit(): String {
        return "contest/submit"
    }

    @RequestMapping("/contest/createIssue")
    fun issue(): String {
        return "contest/createIssue"
    }

    @RequestMapping("/contest/{contestNumber}")
    fun contest(): String {
        return "contest/contest"
    }

    @RequestMapping("contest/{contestNumber}/scoreboard")
    fun contestScore(): String {
        return "contest/scoreboard"
    }

    @RequestMapping("/contest/{contestNumber}/{problemNumber}")
    fun problem(): String {
        return "contest/problem"
    }
}