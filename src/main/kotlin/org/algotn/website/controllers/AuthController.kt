package org.algotn.website.controllers

import org.algotn.website.auth.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class AuthController {

    private val passwordEncoder = BCryptPasswordEncoder()

    @RequestMapping("/login")
    fun login(): String {
        return "auth/login"
    }

    @RequestMapping("/register")
    fun register(@RequestBody user: User): String {
        user.password = passwordEncoder.encode(user.password)
        return "auth/register"
    }
}