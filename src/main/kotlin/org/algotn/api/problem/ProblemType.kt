package org.algotn.api.problem

enum class ProblemType(val type: String) {

    PASS_FAIL("pass-fail"),
    SCORING("scoring");

    companion object {
        fun fromString(type: String): ProblemType {
            return when (type) {
                "pass-fail" -> PASS_FAIL
                "scoring" -> SCORING
                else -> throw IllegalArgumentException("Unknown problem type: $type")
            }
        }
    }
}