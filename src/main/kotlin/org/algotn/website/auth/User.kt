package org.algotn.website.auth

import org.algotn.api.database.data.Data

class User : Data {

    var email: String = ""
    var password: String = ""

    override fun toString(): String {
        return "User(email='$email', password='$password')"
    }
}