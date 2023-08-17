package org.algotn.website.auth

import org.algotn.api.database.data.Data

class User : Data {

    var email: String = ""
    var nickname: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var preferNickname: Boolean = true
    var password: String = ""

    val authorities = mutableListOf<String>()

    fun getPreferredName(): String {
        if(preferNickname) {
            return nickname
        }
        return "$firstName $lastName"
    }
    
    override fun toString(): String {
        return "User(email='$email', nickname='$nickname', password='$password')"
    }
}