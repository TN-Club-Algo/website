package org.algotn.website.auth

import org.algotn.api.database.data.Data
import org.algotn.api.problem.awards.ProblemAward

class User : Data {

    var email: String = ""
    var nickname: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var preferNickname: Boolean = true
    var password: String = ""

    var authorities: ArrayList<String> = ArrayList()

    var awards: ArrayList<ProblemAward> = ArrayList()
    var provider: Provider = Provider.LOCAL

    fun getPreferredName(): String {
        if(preferNickname) {
            return nickname
        }
        return "$firstName $lastName"
    }

    override fun toString(): String {
        return "User(email='$email', nickname='$nickname', firstName='$firstName', lastName='$lastName', preferNickname=$preferNickname, password='$password', authorities=$authorities, awards=$awards)"
    }
}