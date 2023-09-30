package org.algotn.api.problem.awards

abstract class ProblemAward(val problemSlug: String, val date: Long) {

    abstract fun isValid(): Boolean
}