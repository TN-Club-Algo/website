package org.algotn.website.controllers

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class ProblemFormController {

    @GetMapping("/new_problem")
    fun seeForm(): String {
        val pb_map = Chili.getRedisInterface().client.getMap<String, Problem>("problem")
        println(pb_map.keys)
        val pb = pb_map.getOrDefault("test",1)
        println(pb)
        return "new_problem"
    }

    @PostMapping("/new_problem")
    fun sendForm(
        @RequestParam("pbName") pbName: String,
        @RequestParam("statement") statement: String,
        @RequestParam("input") input: String,
        @RequestParam("output") output: String,
    ): String {
        val pbMap = Chili.getRedisInterface().client.getMap<String, String>("problem")

        val newPb = Problem(UUID.randomUUID(),pbName,statement,input,output,"Temps maximal d'exécution : 1s<br>" +
                "Quantité de mémoire maximale : 100 MB", listOf(), listOf(), mapOf())
        val newPbJson = Gson().toJson(newPb)
        pbMap.put(pbName,newPbJson)

        // @todo et all inputs and create problem to add to the map
        return "/new_problem";
    }
}