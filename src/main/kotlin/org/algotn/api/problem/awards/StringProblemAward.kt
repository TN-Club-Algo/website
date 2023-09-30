package org.algotn.api.problem.awards

class StringProblemAward(problemSlug: String, date: Long) : ProblemAward(problemSlug, date) {

    var award: String? = null

    override fun isValid(): Boolean {
        return award != null
    }
}