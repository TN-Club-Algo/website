package org.algotn.website.data

import org.algotn.api.database.data.Data
import org.algotn.website.api.TestJSON

class TestData : Data {

    val solvedProblems = hashSetOf<String>()

    val testsIds = hashSetOf<String>()
    val allTests = mutableListOf<TestJSON>()
    var testInProgress: TestJSON? = null

    var lastTestTimestamp: Long = 0

    fun hasTestInProgress(): Boolean {
        return testInProgress != null
    }

    fun shouldPreventTest(): Boolean {
        // Prevent tests if there is a test in progress or if the last test was less than 1 minute ago
        return testInProgress != null && System.currentTimeMillis() - lastTestTimestamp < 1000 * 60 * 1
    }
}