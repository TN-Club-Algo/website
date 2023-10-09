package org.algotn.website.auth

import org.algotn.api.Chili
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl : UserRepository {

    companion object {

        fun doesUserMatchPassword(userName: String, password: String): Pair<Boolean, List<String>> {
            if (!Chili.getRedisInterface().hasData(userName, User::class.java)) return Pair(false, listOf())
            val user = Chili.getRedisInterface().getData(userName, User::class.java, true)
            if (user!!.provider == Provider.GOOGLE_TN) return Pair(false, listOf())
            val ok = user.password == password
            if (ok) {
                return Pair(true, user.authorities.toList())
            }
            return Pair(false, listOf())
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

    override fun getAllUsers(): List<User> {
        return Chili.getRedisInterface().getAllUsers().toList()
    }
}