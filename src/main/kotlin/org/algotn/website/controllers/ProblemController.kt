package org.algotn.website.controllers

import org.algotn.api.Chili
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView
import java.util.*


@Controller
class ProblemController {

    @GetMapping("/problem/{slug}")
    fun lookProblem(
        @PathVariable("slug") id: String, model: Model
    ): ModelAndView {
        // FIXME
        /*if (!problems.containsKey(id)) {
            return ModelAndView("redirect:/")

        }*/
        val problem = Chili.getProblems().getProblem(id)
        model.addAttribute("problem", problem)
        return ModelAndView("problem")
    }
}