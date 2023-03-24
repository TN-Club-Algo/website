package org.algotn.website.auth

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl: UserRepository {

    override fun save(user: User) {
        TODO("Not yet implemented")
    }

    override fun findByUsername(userName: String): Optional<User> {
        TODO("Not yet implemented")
    }
}