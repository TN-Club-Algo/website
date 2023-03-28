package org.algotn.website.auth

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl : UserRepository {

    override fun save(user: User) {
        //Chili.getRedisInterface().saveData(user.userName, user)
    }

    override fun findByUsername(userName: String): Optional<User> {
        /*Chili.getRedisInterface().getData(userName, User::class.java).let {
            return Optional.ofNullable(it)
        }*/
        return Optional.empty()
    }

    override fun findByEmail(mail: String): Optional<User> {
        /*Chili.getRedisInterface().getData(mail, User::class.java).let {
            return Optional.ofNullable(it)
        }*/
        return Optional.empty()
    }
}