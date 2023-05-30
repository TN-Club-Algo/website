package org.algotn.website.controllers

import org.algotn.api.problem.Example
import org.algotn.api.problem.Problem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import java.util.UUID


@Controller
class ProblemController {

    @GetMapping("/problem")
    fun lookprob(model: Model): ModelAndView {
        val pb_demo = Problem(UUID.randomUUID(),"Strange doors", "Dans votre exploration de ruines interstellaires vous arrivez devant une porte fermer,vous observez un petit moniteur devant celle-ci il y est inscrit un nombre H ainsi qu'une liste L de longeur N d'entier K.\n" +
                "Pour dévérouiller la porte vous devez renvoyer la somme des diviseur de H contenu dans L ayant exactement 2 diviseurs premier.",
            "Sur la premiere ligne \\(0 \\le N \\le 10^5\\) la longeur de la liste<br>" +
                    "Sur la deuxiéme ligne \\(1 \\le H \\le 10^9\\) le nombre dont on cherche les diviseurs <br>" +
                    "Sur la ligne 3, N entiers \\(1 \\le K \\le 10^3\\) séparés par des espaces", "Un entiers égal à la somme des diviseurs de H dans L ayant exactement 2 diviseurs premier.",
            "Temps maximal d'éxécution : 1000 ms<br>" +
                    "Quantités de mémoire maximale : 100000 kilobytes", listOf(Example(UUID.randomUUID(),"10<br>" +
                    "45<br>" +
                    "1 8 3 9 15 9 18 46 20 45","33","Les seul diviseurs de 45 ayants exactement 2 diviseurs premiers sont 9 et 15,<br>" +
                    "il y a 2 fois 9 et 1 fois 15 donc une somme égale à 33.")), listOf(), mapOf()
        )
        val problem = Problem(
            UUID.randomUUID(), "Problem Name (test)", "When \\(a \\ne 0\\), there are two solutions to \\(ax^2 + bx + c = 0\\) and they are\n" +
                    "\$\$x = {-b \\pm \\sqrt{b^2-4ac} \\over 2a}.\$\$ (test)",
            "Problem input (test)", "Problem output (test)",
            "Problem exec_c (test)", listOf(Example(UUID.randomUUID(),"0<br>1 2","2","")), listOf(), mapOf()
        ) // need to be replaced by a real request to the api
        model.addAttribute("problem", pb_demo)
        return ModelAndView("problem")
    }

}