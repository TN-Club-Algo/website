package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class ProblemFormController {

    @GetMapping("/new_problem")
    fun seeForm(): String {
        return "new_problem"
    }

    @PostMapping("/new_problem")
    fun sendForm(
        @RequestParam("prog") prog: String
    ): String {


        return "/new_problem";
    }
}