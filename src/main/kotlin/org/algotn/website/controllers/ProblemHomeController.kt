package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView

@Controller
class ProblemHomeController {
    @GetMapping("/problem")
    fun seeForm(model: Model): ModelAndView {
        val pbMap = ProblemController.problems
        model.addAttribute("keys", pbMap.keys)
        model.addAttribute("problems", pbMap)
        return ModelAndView("problemHome")
    }
}