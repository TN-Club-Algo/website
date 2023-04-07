package org.algotn.website.auth

import java.util.*

interface UserRepository {

    fun removeUser(user: User) {
        removeUser(user.email)
    }

    fun userExists(email: String): Boolean

    fun removeUser(email: String)

    fun save(user: User)

    fun findByUsername(userName: String): Optional<User>

    fun findByEmail(userName: String): Optional<User>
}