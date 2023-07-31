package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class ProblemHomeController {

    private val problemsPerPage = 3

    @GetMapping("/problem")
    fun seeForm(model: Model, @RequestParam("page", defaultValue = "1") page: Int): ModelAndView {
        val pageCount = Chili.getProblems().getProblems().size / problemsPerPage + 1
        model.addAttribute("pageCount", pageCount)
        model.addAttribute("currentPage", page)
        if (page < 1 || page > pageCount) {
            return ModelAndView("redirect:/problem?page=1")
        }

        val subList = Chili.getProblems().sortedProblems.subList(
            (page - 1) * problemsPerPage,
            (page * problemsPerPage).coerceAtMost(Chili.getProblems().sortedProblems.size)
        )
        model.addAttribute("keys", subList.map { it.slug })
        val problems = hashMapOf<String, Problem>()
        subList.forEach {
            problems[it.slug] = it
        }

        model.addAttribute("problems", problems)
        return ModelAndView("problemHome")
    }
}