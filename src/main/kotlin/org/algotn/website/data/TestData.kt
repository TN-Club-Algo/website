package org.algotn.website.data

import org.algotn.api.database.data.Data
import org.algotn.website.api.TestJSON

class TestData : Data {

    // TODO: add test id on submit
    val testsIds = hashSetOf<String>()
    val allTests = mutableListOf<TestJSON>()
    var testInProgress: TestJSON? = null

    fun hasTestInProgress(): Boolean {
        return testInProgress != null
    }
}