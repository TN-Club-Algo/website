package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class jsController {
    @RequestMapping("/get_problems", method = [RequestMethod.GET])
    @ResponseBody
    fun showAllCategories(): String{
        val problems = Chili.getProblems().sortedProblems
        print(problems.toString())
        return problems.toString()
    }
}