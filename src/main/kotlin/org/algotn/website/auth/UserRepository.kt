package org.algotn.website.auth

import java.util.*

interface UserRepository {

    fun save(user: User)

    fun findByUsername(userName: String): Optional<User>
}