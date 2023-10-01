package org.algotn.api.problem.awards

class StringProblemAward(problemSlug: String, date: Long) : ProblemAward(problemSlug, date) {

    var award: String? = null

    override fun isValid(): Boolean {
        return award != null
    }

    override fun clone(): ProblemAward {
        val award = StringProblemAward(problemSlug, System.currentTimeMillis())
        award.award = this.award
        return award
    }

    override fun toString(): String {
        return "StringProblemAward(award=$award)"
    }
}