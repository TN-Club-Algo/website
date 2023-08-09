package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.api.contest.ContestType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

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

    @PostMapping("/contest/submit")
    fun sendForm(
        @RequestParam("pbSelected") problems: String,
        @RequestParam("begDate") begDate: String,
        @RequestParam("endDate") endDate: String,
        @RequestParam("contestName") contestName: String,
        @RequestParam("organisator") organisator: String,
        @RequestParam("desc") desc: String,

        ): String {
        println("form in submit")
        println(problems)
        println(begDate)
        println(endDate)
        println(contestName)
        println(organisator)
        println(desc)
//        println(Instant.parse(begDate))

        val contestMap = Chili.getRedisInterface().client.getMap<String, String>("contest")
        val newContest = Contest()
        newContest.name = contestName
        newContest.organisator = organisator
        newContest.beginning = begDate
        newContest.end = endDate

        for (problem in problems) {
            val pbContest = ContestProblem(problem)
            newContest.addProblem(pbContest)
        }
        /* val pbMap = Chili.getRedisInterface().client.getMap<String, String>("problem")

         val newPb = Problem(UUID.randomUUID(),pbName,statement,input,output,"Temps maximal d'exécution : 1s<br>" +
                 "Quantité de mémoire maximale : 100 MB", listOf(), listOf(), mapOf())
         val newPbJson = Gson().toJson(newPb)
         pbMap.put(pbName,newPbJson)

         // @todo et all inputs and create problem to add to the map*/
        return "/contest/submit";
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