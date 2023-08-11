package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.api.contest.ContestProblem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
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
        @RequestParam("selectedProblems") selectedProblems: List<String>,
        @RequestParam("beginningDate") beginningDate: String,
        @RequestParam("endDate") endDate: String,
        @RequestParam("contestName") contestName: String,
        @RequestParam("creator") creator: String,
        @RequestParam("description") desc: String,

        ): String {
        println("form in submit")
        println(selectedProblems)
//        println(selectedProblems.length)

        println(beginningDate)
        println(endDate)
        println(contestName)
        println(creator)
        println(desc)
//        println(Instant.parse(begDate))


        val newContest = Contest()

        newContest.name = contestName
        newContest.organisator = creator
        newContest.beginning = ZonedDateTime.parse(beginningDate)
        newContest.end = ZonedDateTime.parse(endDate)

//        val problemsJSON = Gson().fromJson<Object>(problems,List)
//        println(problemsJSON)
//        println(problemsJSON.length)
        for (ind in 0.. (selectedProblems.size -1) step  2) {
            println(selectedProblems[ind])
            val pbContest = ContestProblem(selectedProblems[ind],selectedProblems[ind+1].toInt())
            newContest.addProblem(pbContest)
        }
        println(newContest.problems)
        val contestMap = Chili.getRedisInterface().client.getMap<UUID, String>("contest")
        val contestJson = Gson().toJson(newContest)
        contestMap.put(newContest.uuid,contestJson)

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