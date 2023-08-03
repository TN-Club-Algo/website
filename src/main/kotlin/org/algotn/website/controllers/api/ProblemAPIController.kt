package org.algotn.website.controllers.api

import com.google.gson.Gson
import com.google.gson.JsonArray
import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/problems")
class ProblemAPIController {

    @GetMapping("/all")
    fun getProblems(): Array<Problem> {
        return Chili.getProblems().getProblems()
    }

}