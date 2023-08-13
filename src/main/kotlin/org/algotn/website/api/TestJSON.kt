package org.algotn.website.api

data class TestJSON(
    var testID: String,
    var currentIndex: Int,
    var email: String,
    var problemSlug: String,
    var codeURL: String,
    var progress: String,
    var timeElapsed: String,
    var memoryUsed: String,
) {
    constructor() : this("", -1, "", "", "", "", "", "")
}