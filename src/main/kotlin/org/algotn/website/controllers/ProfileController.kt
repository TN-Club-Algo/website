package org.algotn.website.controllers

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProfileController {

    @GetMapping("/profile")
    fun profile(): String {
        return "profiles/profile"
    }

    @GetMapping("/profile/info")
    fun info(): String {
        return "profiles/info"
    }

    @GetMapping("/profile/parameter")
    fun parameter(): String {
        return "profiles/parameter"
    }

    // TODO: si l'utilisateur doit attendre une VM, lui dire qu'il est en file d'attente et envoyer via le websocket à chaque fois qu'il y a un changement

    @GetMapping("/profile/test")
    fun viewOwnTests(model: Model): String {
        val principal = SecurityContextHolder.getContext().authentication.principal

        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        model.addAttribute("email", username)
        return "profiles/test"
    }
}