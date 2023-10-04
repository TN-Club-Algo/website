package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.user.TNOAuth2User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import kotlin.math.ceil

@Controller
class ProblemHomeController {

    @Autowired
    val userRepository: UserRepository? = null

    private val problemsPerPage = 10

    @GetMapping("/problem")
    fun seeForm(model: Model, @RequestParam("page", defaultValue = "1") page: Int): ModelAndView {
        var pageCount = ceil(Chili.getProblems().getProblems().size / problemsPerPage.toDouble()).toInt()
        if (pageCount < 1) pageCount = 1
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

        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val user = userRepository!!.findByUsername(username)
        if (user.isPresent) {
            model.addAttribute("email", username)
        }

        return ModelAndView("problemHome")
    }
}