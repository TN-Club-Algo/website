package org.algotn.website.controllers.api

import org.algotn.website.auth.UserRepository
import org.algotn.website.utils.Authorities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UsersAPIController {

    @Autowired
    val userRepository: UserRepository? = null

    @GetMapping("/all")
    fun getAllUsers(@RequestParam(required = false, defaultValue = "", name = "starts_with") startsWith: String): Map<String, Any> {
        val currentUser = Authorities.getCurrentUser(userRepository!!)
        if (currentUser == null|| !Authorities.hasUsersAuthority(currentUser)) {
            throw AccessDeniedException("")
        }
        val users = userRepository.getAllUsers()

        return mapOf("users" to users)
    }
}