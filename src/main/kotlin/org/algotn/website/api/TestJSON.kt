package org.algotn.website.api

import org.algotn.api.problem.Problem

data class TestJSON(
    val testID: String,
    val currentIndex: Int,
    val email: String,
    val problem: Problem,
    val codeURL: String,
    val progress: String,
    val timeElapsed: String,
    val memoryUsed: String,
)