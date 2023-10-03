package org.algotn.api.problem.awards

class StringProblemAward(problemSlug: String, date: Long) : ProblemAward(problemSlug, date) {

    constructor() : this("", 0)

    constructor(problemSlug: String, date: Long, award: String?) : this(problemSlug, date) {
        this.award = award
    }

    var award: String? = null

    override fun isValid(): Boolean {
        return award != null
    }

    override fun clone(): ProblemAward {
        val award = StringProblemAward(problemSlug, System.currentTimeMillis())
        award.award = this.award
        return award
    }

    override fun display(): String {
        return award ?: "null"
    }

    override fun toString(): String {
        return "StringProblemAward(award=$award)"
    }
}