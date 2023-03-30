package org.algotn.website.auth

import org.algotn.api.Chili
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl : UserRepository {

    companion object {

        fun doesUserMatchPassword(userName: String, password: String): Boolean {
            return true
        }
    }

    override fun removeUser(email: String) {
        Chili.getRedisInterface().removeAllData("users:$email")
    }

    override fun save(user: User) {
        Chili.getRedisInterface().saveData("users:${user.userName}", user)
    }

    override fun findByUsername(userName: String): Optional<User> {
        Chili.getRedisInterface().getData("users:$userName", User::class.java).let {
            return Optional.ofNullable(it)
        }
    }

    override fun findByEmail(mail: String): Optional<User> {
        return findByUsername(mail)
    }
}