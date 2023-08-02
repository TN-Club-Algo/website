package org.algotn.website.controllers

import org.algotn.api.Chili
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class ContestController {

    @GetMapping("/contest")
    fun contestIndex(): String {
        return "contest/contestIndex"
    }

    @GetMapping("/contest/test")
    fun index(model: Model): String {
        model.addAttribute("eventName", "FIFA 2018")
        return "contest/test"
    }
//    @GetMapping("/contest/test")
//    fun test():String{
//        return "contest/test"
//    }
    @GetMapping("/contest/submit")
    fun submit(model: Model): ModelAndView {
//        val printProblems = hashMapOf<String, Problem>()
        val listProblems = Chili.getProblems().sortedProblems//.forEach {
//            printProblems[it.slug] = it
//        }
//        print(printProblems)
        model.addAttribute("keys", listProblems.map { it.slug })
        return ModelAndView("contest/submit")
    }

    @GetMapping("/contest/createIssue")
    fun issue(): String {
        return "contest/createIssue"
    }

    @GetMapping("/contest/{contestNumber}")
    fun contest(): String {
        return "contest/contest"
    }

    @GetMapping("contest/{contestNumber}/scoreboard")
    fun contestScore(): String {
        return "contest/scoreboard"
    }

    @GetMapping("/contest/{contestNumber}/{problemNumber}")
    fun problem(): String {
        return "contest/problem"
    }
}