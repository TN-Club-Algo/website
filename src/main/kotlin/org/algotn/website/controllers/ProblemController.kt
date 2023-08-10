package org.algotn.website.controllers

import org.algotn.api.Chili
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView
import java.util.*


@Controller
class ProblemController {

    @GetMapping("/problem/{slug}")
    fun lookProblem(
        @PathVariable("slug") id: String, model: Model
    ): ModelAndView {
        if (Chili.getProblems().getProblem(id) == null) {
            return ModelAndView("redirect:/")
        }

        val problem = Chili.getProblems().getProblem(id)
        model.addAttribute("problem", problem)

        var problemStatement = problem!!.fullStatement
        if (problem.sampleFiles.samples.isNotEmpty()) {
            problemStatement += "\n## Exemples\n"
            problem.sampleFiles.samples.forEachIndexed { index, sample ->
                problemStatement += "### Exemple ${index + 1}\n"

                if(sample.first.split("\n").maxOf { it.length } < 60) {
                    // 1 table
                    problemStatement += "| Entrée | Sortie |\n"
                    problemStatement += "| --- | --- |\n"
                    problemStatement += "| ${sample.first.replace("\n", "<br>")} |  ${sample.second.replace("\n", "<br>")}  |\n"
                } else {
                    // 2 tables
                    problemStatement += "| Entrée |\n"
                    problemStatement += "| --- |\n"
                    problemStatement += "| ${sample.first.replace("\n", "<br>")} |\n\n"
                    problemStatement += "| Sortie |\n"
                    problemStatement += "| --- |\n"
                    problemStatement += "| ${sample.second.replace("\n", "<br>")} |\n"
                }
            }
        }
        model.addAttribute("problemStatement", problemStatement)
        return ModelAndView("problem")
    }
}