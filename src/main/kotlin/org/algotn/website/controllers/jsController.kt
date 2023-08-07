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
    fun showAllProblems(): String{
        val problems = Chili.getProblems().sortedProblems.map { val cur_map = HashMap<String,Any>();
            cur_map.put("slug",it.slug);
            cur_map.put("difficulty",it.difficulty);
            cur_map.put("author",it.author)
            cur_map.put("type",it.type)
            Gson().toJson(cur_map) }
//        print(problems.toString())
//        val json = ""
//        for (pb in problems){
//            json = j
//        }
        println(Gson().toJson(problems))
        return Gson().toJson(problems)
    }
}