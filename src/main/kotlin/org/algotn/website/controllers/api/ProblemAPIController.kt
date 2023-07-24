package org.algotn.website.controllers.api

import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/problems")
class ProblemAPIController {

    @GetMapping("/all")
    fun getProblems(): Array<Problem> {
        return Chili.getProblems().getProblems()
    }
}