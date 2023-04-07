package org.algotn.website.auth

import org.algotn.api.database.data.UserData
import java.io.Serializable

data class User(
    var userName: String = "",
    var password: String = "",
    override var email: String = "",
) : UserData(), Serializable