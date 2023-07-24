package org.algotn.website.controllers

import org.algotn.api.Chili
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class ProblemHomeController {
    @GetMapping("/problem")
    fun seeForm(model: Model): ModelAndView {
        val pbMap = Chili.getProblems().getProblems()/*
        model.addAttribute("keys", pbMap.toList().map { it.slug })
        model.addAttribute("problems", pbMap)*/
        return ModelAndView("problemHome")
    }
}