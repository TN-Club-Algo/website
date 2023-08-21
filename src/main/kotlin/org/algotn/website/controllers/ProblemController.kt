package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.api.utils.DateUtils
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*


@Controller
class ProblemController {

    @GetMapping("/problem/{slug}")
    fun lookProblem(
        @PathVariable("slug") id: String, model: Model,
        redirectAttributes: RedirectAttributes
    ): ModelAndView {
        if (Chili.getProblems().getProblem(id) == null) {
            return ModelAndView("redirect:/")
        }

        val problem = Chili.getProblems().getProblem(id)
        model.addAttribute("problem", problem)

        val contests = Chili.getRedisInterface().getAllUUIDData(Contest::class.java).toMutableList()
            .filter { it.problems.containsKey(id) }
        val currentContests = contests.filter {
            DateUtils.dateToLong(it.beginning) < System.currentTimeMillis()
                    && DateUtils.dateToLong(it.end) > System.currentTimeMillis()
        }.toMutableList()

        val upcomingContests = contests.filter {
            DateUtils.dateToLong(it.beginning) > System.currentTimeMillis()
        }.toMutableList()

        if (upcomingContests.isNotEmpty()) {
            /*model.addAttribute("not_available", true)
            return ModelAndView("problem")*/
            redirectAttributes.addFlashAttribute("not_available", "Ce problème n'est pas encore disponible !")
            return ModelAndView("redirect:/problem")
        }

        if (currentContests.any { it.problems.containsKey(id) }) {
            model.addAttribute("linkedContestsNames", currentContests.joinToString(", ") { it.name })
        }

        var problemStatement = problem!!.fullStatement
        if (problem.sampleFiles.samples.isNotEmpty()) {
            problemStatement += "\n## Exemples\n"
            problem.sampleFiles.samples.forEachIndexed { index, sample ->
                problemStatement += "### Exemple ${index + 1}\n"

                if (sample.first.split("\n").maxOf { it.length } < 60) {
                    // 1 table
                    problemStatement += "| Entrée | Sortie |\n"
                    problemStatement += "| --- | --- |\n"
                    problemStatement += "| ${sample.first.replace(Regex("\r\n|\n"), "<br>")} |  ${
                        sample.second.replace(
                            "\n",
                            "<br>"
                        )
                    }  |\n"
                } else {
                    // 2 tables
                    problemStatement += "| Entrée |\n"
                    problemStatement += "| --- |\n"
                    problemStatement += "| ${sample.first.replace(Regex("\r\n|\n"), "<br>")} |\n\n"
                    problemStatement += "| Sortie |\n"
                    problemStatement += "| --- |\n"
                    problemStatement += "| ${sample.second.replace(Regex("\r\n|\n"), "<br>")} |\n"
                }
            }
        }
        model.addAttribute("problemStatement", problemStatement)
        return ModelAndView("problem")
    }
}