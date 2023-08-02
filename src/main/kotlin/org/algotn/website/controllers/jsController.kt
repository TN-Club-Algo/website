package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class jsController {
    @GetMapping("/get_problems")
    @ResponseBody
    fun showAllCategories(): String{
        val problems = Chili.getProblems().sortedProblems.map { it.slug }
//        print(problems.toString())
//        val json = ""
//        for (pb in problems){
//            json = j
//        }
        print(Gson().toJson(problems))
        return Gson().toJson(problems)
    }
}