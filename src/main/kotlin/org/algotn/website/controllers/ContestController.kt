package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.api.contest.ContestProblem
import org.algotn.api.problem.Problem
import org.algotn.api.utils.DateUtils
import org.algotn.website.auth.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


@Controller
class ContestController {

    @Autowired
    val userRepository: UserRepository? = null

    @GetMapping("/contest")
    fun contestIndex(model: Model): ModelAndView {
        val contests = Chili.getRedisInterface().getAllUUIDData(Contest::class.java).toMutableList()
        val currentContests = contests.filter {
            DateUtils.dateToLong(it.beginning) < System.currentTimeMillis()
                    && DateUtils.dateToLong(it.end) > System.currentTimeMillis()
        }.toMutableList()

        // Remove past contests
        contests.removeIf {
            DateUtils.dateToLong(it.end) < System.currentTimeMillis()
        }
        currentContests.removeIf {
            DateUtils.dateToLong(it.end) < System.currentTimeMillis()
        }

        // Sort contests
        contests.toMutableList().sortBy { it.beginning }
        currentContests.toMutableList().sortBy { it.beginning }

        val principal = SecurityContextHolder.getContext().authentication.principal

        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        val user = userRepository!!.findByUsername(username)

        if (user.isPresent) {
            val userContests = contests.filter { it.registeredUser.contains(username) }
            model.addAttribute("userContests", userContests)
        }

        model.addAttribute("currentContests", currentContests)
        model.addAttribute("contests", contests)

        return ModelAndView("contest/contestIndex")
    }

    @GetMapping("/contest/{uuid}")
    fun seeContest(
        @PathVariable("uuid") uuid: String,
        redirectAttributes: RedirectAttributes,
        model: Model
    ): ModelAndView {
        if (!Chili.getRedisInterface().hasData(uuid, Contest::class.java)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Compétition introuvable")
            return ModelAndView("redirect:/contest")
        }

        val theContest = Chili.getRedisInterface().getData(uuid, Contest::class.java)!!

//        println(theContest.description)
//        theContest.description?.replace("(\r\n|\n)", "<br />")
//        println(theContest.description)

        // Contest must be started
        if (DateUtils.dateToLong(theContest.beginning) > System.currentTimeMillis()) {
            redirectAttributes.addFlashAttribute("errorMessage", "La compétition n'a pas encore commencé !")
            return ModelAndView("redirect:/contest")
        }

        val listProblems = Chili.getProblems().sortedProblems

        val contestProblems = mutableListOf<HashMap<String, Any>>()
//            hashMapOf<String, Problem>()
//
        for (problem in listProblems) {
            if (theContest.problems.containsKey(problem.slug)) {
                val mapProblem = HashMap<String, Any>();
                val scoreProblem = theContest.problems[problem.slug]
                mapProblem["problem"] = problem
                if (scoreProblem != null) {
                    mapProblem["score"] = scoreProblem
                } else {
                    mapProblem["score"] = "undefined"
                }
                contestProblems.add(mapProblem)
            }
        }


        model.addAttribute("allProblem", contestProblems)
        model.addAttribute("contest", theContest)

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
        val newContest = Contest()

        newContest.name = contestName
        newContest.organisator = creator
        newContest.beginning = beginningDate
        newContest.end = endDate
        newContest.description = desc

        for (ind in selectedProblems.indices step 2) {
            val pbContest = ContestProblem(selectedProblems[ind], selectedProblems[ind + 1].toInt())
            newContest.addProblem(pbContest)
        }
        val chili = Chili.getRedisInterface()
        chili.saveData(newContest.uuid, newContest)

        return "/contest/submit";
    }


    @GetMapping("/contest/createIssue")
    fun issue(): String {
        return "contest/createIssue"
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