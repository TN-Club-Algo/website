package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class DefaultController {

    @GetMapping("/")
    fun index(model: Model): ModelAndView {
        model.addAttribute("keys", listOf(Pair("https://res.cloudinary.com/practicaldev/image/fetch/s--MZvaMEOV--/c_imagga_scale,f_auto,fl_progressive,h_420,q_auto,w_1000/https://dev-to-uploads.s3.amazonaws.com/uploads/articles/qll2w5atklv13ljmqope.png", "/problem")
            , Pair("https://wallpaperaccess.com/full/254361.jpg", "/test")))
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