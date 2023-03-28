package org.algotn.website.controllers

import org.algotn.website.auth.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class AuthController {

    private val passwordEncoder = BCryptPasswordEncoder()

    @GetMapping("/login")
    fun login(): String {
        return "auth/login"
    }

    @GetMapping("/register")
    fun register(): String {
        return "auth/register"
    }

    @PostMapping("/register")
    fun register(@ModelAttribute user: User, redirectAttributes: RedirectAttributes): String {
        user.password = passwordEncoder.encode(user.password)

        redirectAttributes.addFlashAttribute("successMessage", "User created successfully.")

        return "redirect:/login"
    }

}