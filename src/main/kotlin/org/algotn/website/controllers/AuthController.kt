package org.algotn.website.controllers

import org.algotn.website.auth.User
import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.WebSecurityConfig
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class AuthController(private val userRepository: UserRepository) {

    private val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping("/password-reset/{token}")
    fun passwordResetToken(
        @PathVariable("token") token: String,
        @RequestParam allParams: Map<String, String>,
        redirectAttributes: RedirectAttributes
    ): String {
        // token exists?

        if (!allParams.containsKey("confirm-password") || allParams["confirm-password"] != allParams["password"]) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.")
            return "redirect:/password-reset/$token"
        }

/*        val user = userRepository.findByUsername(token)

        user.password = passwordEncoder.encode(allParams["password"]!!)

        userRepository.save(user)

        authWithAuthManager(user.userName, user.password)

        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe modifié avec succès.")*/

        return "redirect:/"
    }

    @GetMapping("/password-reset/{token}")
    fun passwordResetToken(@PathVariable("token") token: String): String {
        // token exists?
        return "auth/password-reset-form"
    }

    @GetMapping("/password-reset")
    fun passwordReset(): String {
        return "auth/password-reset"
    }

    @PostMapping("/password-reset")
    fun passwordReset(
        @RequestParam allParams: Map<String, String>,
        redirectAttributes: RedirectAttributes
    ): String {
        if (!userRepository.userExists(allParams["userName"]!!)) {
            redirectAttributes.addFlashAttribute("errorMessage", "L'adresse email renseignée n'est associé à aucun compte.")
            return "redirect:/password-reset"
        }

        redirectAttributes.addFlashAttribute(
            "successMessage",
            "Vous allez recevoir un mail contenant la demande de réinitialisation du mot de passe."
        )

        return "redirect:/password-reset"
    }

    @GetMapping("/login")
    fun login(): String {
        return "auth/login"
    }

    @GetMapping("/register")
    fun register(): String {
        return "auth/register"
    }

    @PostMapping("/register")
    fun register(
        @ModelAttribute user: User,
        @RequestParam allParams: Map<String, String>,
        redirectAttributes: RedirectAttributes
    ): String {
        if (!validateEmail(allParams["userName"]!!)) {
            redirectAttributes.addFlashAttribute("errorMessage", "L'adresse email n'est pas valide.")
            return "redirect:/register"
        }

        if (!allParams.containsKey("confirm-password") || allParams["confirm-password"] != user.password) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.")
            return "redirect:/register"
        }


        user.password = passwordEncoder.encode(user.password)
        user.email = user.userName

        userRepository.save(user)

        authWithAuthManager(user.userName, user.password)

        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé avec succès.")

        return "redirect:/login"
    }

    private fun authWithAuthManager(username: String?, password: String?) {
        val authToken = UsernamePasswordAuthenticationToken(username, password)
        val authentication: Authentication = WebSecurityConfig.authenticationManager().authenticate(authToken)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun validateEmail(email: String): Boolean {
        val regexPattern = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$"
        val regex = Regex(regexPattern)
        return regex.matches(email)
    }
}