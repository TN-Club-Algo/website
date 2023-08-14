package org.algotn.website.api

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.io.Serializable


@JsonDeserialize(`as` = TestJSON::class)
@JsonSubTypes.Type(value = TestJSON::class)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
data class TestJSON(
    var testID: String,
    var currentIndex: Int,
    var email: String,
    var problemSlug: String,
    var codeURL: String,
    var progress: String,
    var timeElapsed: String,
    var memoryUsed: String,
    var validated: Boolean = false
) : Serializable {
    constructor() : this("", -1, "", "", "", "", "", "")
}