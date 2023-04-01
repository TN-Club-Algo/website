package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController {

    @GetMapping("/login")
    fun login(): String {
        return "auth/login"
    }

    @GetMapping("/register")
    fun register(): String {
        return "auth/register"
    }

    @GetMapping("/passwordRecovery")
    fun passwordRecovery(): String {
        return "auth/passwordRecovery"
    }
}