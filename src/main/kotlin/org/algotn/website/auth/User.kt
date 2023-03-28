package org.algotn.website.auth

import java.io.Serializable
import java.util.*

data class User(
    val uuid: UUID = UUID.randomUUID(),
    var userName: String = "",
    var password: String = "",
    val email: String = "",
) : Serializable
