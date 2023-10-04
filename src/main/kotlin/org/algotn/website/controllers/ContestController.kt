package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.api.contest.ContestProblem
import org.algotn.api.utils.DateUtils
import org.algotn.website.auth.User
import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.user.TNOAuth2User
import org.algotn.website.data.TestData
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
                    || DateUtils.dateToLong(it.beginning) > System.currentTimeMillis()
        }.toMutableList()

        val pastContests = contests.filter {
            DateUtils.dateToLong(it.end) < System.currentTimeMillis()
//                    && DateUtils.dateToLong(it.end) > System.currentTimeMillis()
        }.toMutableList()


        // Sort pastContests
        pastContests.toMutableList().sortBy { it.beginning }
        currentContests.toMutableList().sortBy { it.beginning }

        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val user = userRepository!!.findByUsername(username)

        if (user.isPresent) {
            val userContests = contests.filter { it.registeredUser.contains(username) }
            model.addAttribute("userContests", userContests)
        }

        if (user.isPresent) {
            model.addAttribute(
                "hasAuthority",
                user.get().authorities.contains("CONTEST")
            )
        }
        model.addAttribute("currentContests", currentContests)
        model.addAttribute("pastContests", pastContests)

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



        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val user = userRepository!!.findByUsername(username)
        if (user.isPresent) {
            model.addAttribute("email", username)
        }

        return ModelAndView("contest/viewContest")
    }

    @PostMapping("contestRegister/{uuid}")
    fun register(
        @PathVariable("uuid") uuid: String
    ): String {
        println(uuid)

        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val chili = Chili.getRedisInterface()
        if (!chili.hasData(uuid, Contest::class.java)) {
            return "redirect:/contest"
        }
        val contest = chili.getData(uuid, Contest::class.java)!!

        contest.registeredUser.add(username)

        contest.addUserToLeaderboard(username)

        // Look if the user hasn't already solved a problem
        val testData = Chili.getRedisInterface().getData(username, TestData::class.java)!!
        contest.problems.keys.forEach {
            if (testData.solvedProblems.contains(it)) {
                val score = contest.computeProblemScore(it)
                contest.updateUserScore(it, score, username)
            }
        }


        chili.saveData(uuid, contest)
//@todo edit this after merge
//        val user = UserGroup!!.findByUsername(username)
//        if (!user.isPresent) {
//            return mapOf("error" to "Authentication error", "success" to false)
//        }

        return "/contest"
    }

    @GetMapping("/contest/submit")
    fun submit(model: Model): ModelAndView {
        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val user = Chili.getRedisInterface().getData(username, User::class.java)
        if (!user!!.authorities.contains("CONTEST")) return ModelAndView("redirect:/")

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
        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val user = Chili.getRedisInterface().getData(username, User::class.java)!!
        print(user.authorities.contains("CONTEST"))
        if (!user.authorities.contains("CONTEST")) return "redirect:/"

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

        return "redirect:/contest";
    }

    @GetMapping("/contest/leaderboard/{uuid}")
    fun lookResult(
        @PathVariable("uuid") id: String, model: Model
    ): ModelAndView {
        if (Chili.getRedisInterface().getData(id, Contest::class.java) == null) {
            return ModelAndView("redirect:/")
        }

        /*val principal = SecurityContextHolder.getContext().authentication.principal

        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }*/

        if (!Chili.getRedisInterface().hasData(id, Contest::class.java)) {
            return ModelAndView("redirect:/contest")
        }
        val contest = Chili.getRedisInterface().getData(id, Contest::class.java)!!
        val emailMap = Chili.getRedisInterface().client.getMap<String, String>("user-emails")
        val leaderboard = LinkedHashMap<String, Double>()
        contest.registeredUser.forEach {
            leaderboard[it] = contest.getUserScore(it)
        }

        val nicknameMap = mutableMapOf<String, String>()
        contest.registeredUser.forEach {
            nicknameMap[it] = emailMap[it] ?: it
        }

        // FIXME: don't do that!
        val sortedLeaderboard = leaderboard.toList().sortedByDescending { (_, value) -> value }.toMap()

        model.addAttribute("slug", id)
        model.addAttribute("name", contest.name)
        model.addAttribute("users", sortedLeaderboard.keys)
        model.addAttribute("leaderboard", sortedLeaderboard)
        model.addAttribute("nicknames", nicknameMap)

        return ModelAndView("contestLead")
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