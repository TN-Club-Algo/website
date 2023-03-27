package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class AuthController {

    @RequestMapping("/login")
    fun login(): String {
        return "auth/login"
    }

    @RequestMapping("/register")
    fun register(): String {
        return "auth/register"
    }

    @RequestMapping("/passwordRecovery")
    fun passwordRecovery(): String {
        return "auth/passwordRecovery"
    }
}