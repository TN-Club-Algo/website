package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DefaultController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/error")
    fun error(): String {
        return "default/error"
    }

    @GetMapping("/scoreboard")
    fun scoreboard(): String {
        return "default/scoreboard"
    }

    @GetMapping("/legalNotice")
    fun mentionLegal(): String {
        return "default/legalNotice"
    }

    @GetMapping("/contact")
    fun contact(): String {
        return "default/contact"
    }
}