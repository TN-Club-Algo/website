package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ContestController {

    @GetMapping("/contest")
    fun contestIndex(): String {
        return "contest/contestIndex"
    }

    @GetMapping("/contest/submit")
    fun submit(): String {
        return "contest/submit"
    }

    @GetMapping("/contest/createIssue")
    fun issue(): String {
        return "contest/createIssue"
    }

    @GetMapping("/contest/{contestNumber}")
    fun contest(): String {
        return "contest/contest"
    }

    @GetMapping("contest/{contestNumber}/scoreboard")
    fun contestScore(): String {
        return "contest/scoreboard"
    }

    @GetMapping("/contest/{contestNumber}/{problemNumber}")
    fun problem(): String {
        return "contest/problem"
    }
}