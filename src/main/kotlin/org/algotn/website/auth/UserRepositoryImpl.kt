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
        //Chili.getRedisInterface().removeAllData(email)
    }

    override fun userExists(email: String): Boolean {
        return Chili.getRedisInterface().hasData(email, User::class.java)
    }

    override fun save(user: User) {
        Chili.getRedisInterface().saveData(user.email, user)
    }

    override fun findByUsername(userName: String): Optional<User> {
        if (!userExists(userName)) return Optional.empty()
        Chili.getRedisInterface().getData(userName, User::class.java).let {
            return Optional.ofNullable(it)
        }
    }

    override fun findByEmail(userName: String): Optional<User> {
        return findByUsername(userName)
    }
}