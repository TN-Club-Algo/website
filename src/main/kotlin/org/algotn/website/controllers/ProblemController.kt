package org.algotn.website.controllers

import org.algotn.website.Problem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.util.UUID

@Controller
class ProblemController {

    @RequestMapping("/problem")
    fun login(model: Model): ModelAndView {
        val problem = Problem(
            UUID.randomUUID(), "Problem Name (test)", "Problem Statement (test)",
            "Problem input (test)", "Problem output (test)", "Problem input_c (test)",
            "Problem exec_c (test)"
        ) // need to be replaced by a real request to the api
        model.addAttribute("problem", problem)
        return ModelAndView("problem")
    }

}