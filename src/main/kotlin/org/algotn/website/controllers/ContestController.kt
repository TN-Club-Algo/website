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
        val gson = Gson()
        val mydata = Chili.getRedisInterface().getAllUUIDData(Contest().javaClass).map{
            val curMap = HashMap<String,Any>();
            curMap.put("uuid",it.uuid)
            curMap.put("contestName",it.name);
            curMap.put("beginning",it.beginning);
            curMap.put("end",it.end)
            curMap.put("nbUser",it.registeredUser.size)
            curMap
        }
        println("please")
        println(mydata)
        println(mydata[0])
        print("yeah")
//        val hashContest = Chili.getRedisInterface().client.getMap<String,Contest>("contest")
//        val keysContest = hashContest.keys
//        println(keysContest)
//        hashContest[keysContest.first()]?.toMap()
//        println("ok")
//        model.addAttribute("keys",hashContest.keys)
        model.addAttribute("contests",mydata)

//        print("yeah")
        return ModelAndView("contest/contestIndex")
    }

    @PostMapping("contestRegister/{uuid}")
    fun register(
        @PathVariable("uuid") uuid:String): String{
        println(uuid)

        val principal = SecurityContextHolder.getContext().authentication.principal

        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        val chili = Chili.getRedisInterface()
        val contest = chili.getData(uuid,Contest().javaClass)

        contest?.registeredUser?.add(username)

        if (contest != null) {
            chili.saveData(uuid,contest)
        }

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
//        newContest.beginning = ZonedDateTime.parse(beginningDate)
//        newContest.end = ZonedDateTime.parse(endDate)

//        val problemsJSON = Gson().fromJson<Object>(problems,List)
//        println(problemsJSON)
//        println(problemsJSON.length)
        for (ind in 0.. (selectedProblems.size -1) step  2) {
            println(selectedProblems[ind])
            val pbContest = ContestProblem(selectedProblems[ind],selectedProblems[ind+1].toInt())
            newContest.addProblem(pbContest)
        }
        println(newContest.problems)
        val chili = Chili.getRedisInterface()
        chili.saveData(newContest.uuid.toString(),newContest)
//        val contestMap = Chili.getRedisInterface().client.getMap<UUID, Contest>("contest")
////        val contestJson = Gson().toJson(newContest)
//        contestMap.put(newContest.uuid,newContest)

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