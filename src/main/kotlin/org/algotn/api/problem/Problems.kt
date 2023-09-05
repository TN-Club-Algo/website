package org.algotn.api.problem

import org.algotn.api.Chili
import org.algotn.api.problem.reader.ICPCProblemReader
import org.algotn.api.utils.getLatestUpdateTime
import java.io.File

class Problems {

    private val problems = hashMapOf<String, Problem>()

    val sortedProblems = arrayListOf<Problem>()

    init {
        val icpcProblemReader = ICPCProblemReader()

        val problemFolder = File(Chili.SAVE_LOCATION + "/problems/")
        if (problemFolder.exists()) {
            problemFolder.listFiles()?.forEach {
                if (it.isDirectory) {
                    val problem = icpcProblemReader.readProblem(File(it, "problem.yml"))
                    problems[problem.slug] = problem

                    sortedProblems.add(problem)
                }
            }
        }

        sortedProblems.sortByDescending { File(it.getDirectory()).getLatestUpdateTime() }
    }

    fun getProblem(slug: String): Problem? {
        return problems[slug]
    }

    fun getProblems(): Array<Problem> {
        return problems.values.toTypedArray()
    }
}