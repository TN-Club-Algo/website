package org.algotn.website.auth

import org.algotn.api.database.data.UserData
import java.util.*

class User(private val uuid: UUID, var userName: String, var password: String, override val email: String): UserData() {

}