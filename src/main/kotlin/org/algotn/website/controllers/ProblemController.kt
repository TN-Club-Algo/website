package org.algotn.website.controllers

import org.algotn.api.problem.Example
import org.algotn.api.problem.Problem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import java.util.UUID


@Controller
class ProblemController {

    @GetMapping("/problem")
    fun lookprob(model: Model): ModelAndView {
        val problem = Problem(
            UUID.randomUUID(), "Problem Name (test)", "When \\(a \\ne 0\\), there are two solutions to \\(ax^2 + bx + c = 0\\) and they are\n" +
                    "\$\$x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}.\$\$ (test)",
            "Problem input (test)", "Problem output (test)",
            "Problem exec_c (test)", listOf(Example(UUID.randomUUID(),"0<br>1 2","2","")), listOf()
        ) // need to be replaced by a real request to the api
        model.addAttribute("problem", problem)
        return ModelAndView("problem")
    }

}