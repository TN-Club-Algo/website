package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class DefaultController {

    @RequestMapping("/")
    fun index(): String {
        return "index"
    }

    @RequestMapping("/scoreboard")
    fun scoreboard(): String {
        return "default/scoreboard"
    }

    @RequestMapping("/mentionLegal")
    fun mentionLegal(): String {
        return "default/mentionLegal"
    }

    @RequestMapping("/contact")
    fun contact(): String {
        return "default/contact"
    }
}