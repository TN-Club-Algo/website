package org.algotn.website.controllers

import org.algotn.website.auth.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class ProfileController {

    @Autowired
    val userRepository: UserRepository? = null

    @GetMapping("/profile")
    fun profile(model: Model): ModelAndView {
        val principal = SecurityContextHolder.getContext().authentication.principal

        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        val user = userRepository!!.findByUsername(username)
        if (!user.isPresent) {
            return ModelAndView("redirect:/login")
        }

        model.addAttribute("user", user.get())

        return ModelAndView("profiles/profile")
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