package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.api.contest.ContestProblem
import org.algotn.api.users.UserGroup
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.HashMap


@Controller
class ContestController {

    @GetMapping("/contest")
    fun contestIndex(model: Model): ModelAndView {
        println("coucou")
        val allContest = Chili.getRedisInterface().getAllUUIDData(Contest()::class.java).map {
            val curMap = HashMap<String, Any>();
            curMap["uuid"] = it.uuid
            curMap["contestName"] = it.name;
            curMap["beginning"] = it.beginning;
            curMap["end"] = it.end
            curMap["nbUser"] = it.registeredUser.size
            curMap
        }
        if (!allContest.isNullOrEmpty()) {
            println("please")
            println(allContest)
            println(allContest[0])
            print("yeah")
        }
        model.addAttribute("contests", allContest)

        return ModelAndView("contest/contestIndex")
    }

    @GetMapping("/contest/seeContest/{uuid}")
    fun seeContest(
        @PathVariable("uuid") uuid: String,
        model: Model
    ): ModelAndView {
        println("begining the contest show")
        println(uuid)
        val theContest = Chili.getRedisInterface().getData(uuid,Contest()::class.java)

        println(theContest)

        val listProblems = Chili.getProblems().sortedProblems//.forEach {

        val contestProblems = mutableListOf<HashMap<String,Any>>()
        for (problem in listProblems){
            if (theContest != null && theContest.problems.containsKey(problem.slug)){
                val mapProblem = HashMap<String, Any>();
                val scoreProblem = theContest.problems[problem.slug]
                mapProblem["name"] = problem.name;
                mapProblem["slug"] = problem.slug
                mapProblem["difficulty"] = problem.difficulty;
                mapProblem["keywords"] = problem.keywords
                mapProblem["author"] = problem.author
                mapProblem["type"] = problem.type
                if (scoreProblem != null){
                    mapProblem["score"] = scoreProblem
                }else{
                    mapProblem["score"] = "undefined"
                }
                contestProblems.add(mapProblem)
                println(mapProblem.toString())
            }
        }


//        model.addAttribute("allProblemNames", listProblems.map { it.slug })
        model.addAttribute("allProblem",contestProblems)
        model.addAttribute("contest",theContest)

        return ModelAndView("contest/viewContest")
    }

    @PostMapping("contestRegister/{uuid}")
    fun register(
        @PathVariable("uuid") uuid: String
    ): String {
        println(uuid)

        val principal = SecurityContextHolder.getContext().authentication.principal

        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        val chili = Chili.getRedisInterface()
        val contest = chili.getData(uuid, Contest().javaClass)

        contest?.registeredUser?.add(username)

        if (contest != null) {
            chili.saveData(uuid, contest)
        }
//@todo edit this after merge
//        val user = UserGroup!!.findByUsername(username)
//        if (!user.isPresent) {
//            return mapOf("error" to "Authentication error", "success" to false)
//        }

        return "/contest"
    }


    @GetMapping("/contest/test")
    fun index(model: Model): String {
        model.addAttribute("eventName", "FIFA 2018")
        return "contest/test"
    }

    @GetMapping("/contest/submit")
    fun submit(model: Model): ModelAndView {

        val listProblems = Chili.getProblems().sortedProblems//.forEach {
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

        println(beginningDate)
        println(endDate)
        println(contestName)
        println(creator)
        println(desc)


        val newContest = Contest()

        newContest.name = contestName
        newContest.organisator = creator
        newContest.beginning = beginningDate
        newContest.end = endDate
        newContest.description = desc

        for (ind in 0..(selectedProblems.size - 1) step 2) {
            println(selectedProblems[ind])
            val pbContest = ContestProblem(selectedProblems[ind], selectedProblems[ind + 1].toInt())
            newContest.addProblem(pbContest)
        }
        println(newContest.problems)
        val chili = Chili.getRedisInterface()
        chili.saveData(newContest.uuid.toString(), newContest)

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