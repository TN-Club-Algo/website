package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class DefaultController {

    @GetMapping("/")
    fun index(model: Model): ModelAndView {
        model.addAttribute("keys", listOf(Pair("/api/image/Algo_TNEvent.png", "/contest/")))
        return ModelAndView("index")
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