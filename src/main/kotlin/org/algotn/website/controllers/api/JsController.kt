package org.algotn.website.controllers.api

import com.google.gson.Gson
import org.algotn.api.Chili
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.util.UUID

@Controller
class JsController {
    @GetMapping("/get_problems")
    @ResponseBody
    fun showAllProblems(): String {
        val gson = Gson()
        val problems = Chili.getProblems().sortedProblems.map {
            val curMap = HashMap<String, Any>();
            curMap.put("slug", it.slug);
            curMap.put("difficulty", it.difficulty);
            curMap.put("author", it.author)
            curMap.put("type", it.type)
            gson.toJson(curMap)
        }
        println(gson.toJson(problems))
        return gson.toJson(problems)
    }
//    @GetMapping("/get_contest")
//    @ResponseBody
//    fun showAllContest(): String{
//        val gson = Gson()
//        val contestMap = Chili.getRedisInterface().client.getMap<String,String>("contest")
//        for ((uuid,contest) in contestMap) {
//
//        }
////          .getProblems().sortedProblems.map {
////            val curMap = HashMap<String,Any>();
////            curMap.put("slug",it.slug);
////            curMap.put("difficulty",it.difficulty);
////            curMap.put("author",it.author)
////            curMap.put("type",it.type)
////            gson.toJson(curMap)
////        }
////        print(problems.toString())
////        val json = ""
////        for (pb in problems){
////            json = j
////        }
//        println(gson.toJson(contestMap))
//        return gson.toJson(contestMap)
//    }
}