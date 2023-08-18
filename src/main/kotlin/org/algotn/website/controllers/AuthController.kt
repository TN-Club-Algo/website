package org.algotn.website.controllers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.algotn.api.Chili
import org.algotn.website.auth.User
import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.WebSecurityConfig
import org.algotn.website.services.email.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import java.util.concurrent.TimeUnit


@Controller
class AuthController(private val userRepository: UserRepository) {

    private val passwordEncoder = BCryptPasswordEncoder()

    @Autowired
    private var webSecurityConfig: WebSecurityConfig? = null

    @Autowired
    private var emailService: EmailService? = null

    @PostMapping("/password-reset/{token}")
    fun passwordResetToken(
        @PathVariable("token") token: String,
        @RequestParam allParams: Map<String, String>,
        redirectAttributes: RedirectAttributes
    ): String {
        if (!allParams.containsKey("password-confirm") || allParams["password-confirm"] != allParams["password"]) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.")
            return "redirect:/password-reset/$token"
        }

        // Check if token exists and is linked to this user
        if (!Chili.getRedisInterface().client.getMapCache<String, String>("password-reset-tokens").containsKey(token)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le token n'est pas valide.")
            return "redirect:/password-reset/$token"
        }

        val email = Chili.getRedisInterface().client.getMapCache<String, String>("password-reset-tokens")[token]!!
        Chili.getRedisInterface().client.getMapCache<String, String>("password-reset-tokens").remove(token)

        val user = userRepository.findByEmail(email).get()

        user.password = passwordEncoder.encode(allParams["password"]!!)

        userRepository.save(user)

        authWithAuthManager(user.email, user.password)

        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe modifié avec succès.")

        return "redirect:/login"
    }

    @GetMapping("/password-reset/{token}")
    fun passwordResetToken(@PathVariable("token") token: String): String {
        if (!Chili.getRedisInterface().client.getMapCache<String, String>("password-reset-tokens").containsKey(token)) {
            return "redirect:/"
        }
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
            redirectAttributes.addFlashAttribute(
                "errorMessage",
                "L'adresse email renseignée n'est associé à aucun compte."
            )
            return "redirect:/password-reset"
        }

        redirectAttributes.addFlashAttribute(
            "successMessage",
            "Vous allez recevoir un mail contenant la demande de réinitialisation du mot de passe."
        )

        var token = UUID.randomUUID()
        while (Chili.getRedisInterface().client.getMapCache<String, String>("password-reset-tokens")
                .containsKey(token.toString())
        )
            token = UUID.randomUUID()

        val map = Chili.getRedisInterface().client.getMapCache<String, String>("password-reset-tokens")

        // Remove previous token if exists
        map.entries.filter { it.value == allParams["userName"]!! }.forEach { map.remove(it.key) }

        map[token.toString()] = allParams["userName"]!!
        // Remove after 10 minutes
        map.updateEntryExpiration(token.toString(), 10, TimeUnit.MINUTES, 0, TimeUnit.MINUTES)


        GlobalScope.launch {
            emailService!!.sendPasswordResetEmail(allParams["userName"]!!, token.toString())
        }

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
        @RequestBody allParams: MultiValueMap<String, String>,
        redirectAttributes: RedirectAttributes
    ): String {
        if (allParams.getFirst("userName") == null || allParams.getFirst("password") == null || allParams.getFirst("nickname") == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Veuillez renseigner tous les champs.")
            return "redirect:/register"
        }

        if (!validateEmail(allParams.getFirst("userName")!!)) {
            redirectAttributes.addFlashAttribute("errorMessage", "L'adresse email n'est pas valide.")
            return "redirect:/register"
        }

        // TODO: dynamic check in js
        val emailMap = Chili.getRedisInterface().client.getMap<String, String>("user-emails")
        if (emailMap.containsKey(allParams.getFirst("userName")!!)) {
            redirectAttributes.addFlashAttribute("errorMessage", "L'email renseignée est déjà liée un compte.")
            return "redirect:/register"
        }
        val nicknameMap = Chili.getRedisInterface().client.getMap<String, String>("user-nicknames")
        if (nicknameMap.containsKey(allParams.getFirst("nickname")!!)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le surnom désiré n'est pas disponible.")
            return "redirect:/register"
        }

        val user = User()
        user.email = allParams.getFirst("userName")!!
        user.password = allParams.getFirst("password")!!
        user.nickname = allParams.getFirst("nickname")!!

        if (!allParams.containsKey("confirm-password") || allParams.getFirst("confirm-password") != user.password) {
            redirectAttributes.addFlashAttribute("errorMessage", "Les mots de passe ne correspondent pas.")
            return "redirect:/register"
        }

        nicknameMap[user.nickname] = user.email
        emailMap[user.email] = user.nickname

        user.password = passwordEncoder.encode(user.password)

        userRepository.save(user)

        authWithAuthManager(user.email, user.password)

        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur créé avec succès.")

        return "redirect:/login"
    }

    private fun authWithAuthManager(username: String?, password: String?) {
        val authToken = UsernamePasswordAuthenticationToken(username, password)
        val authentication: Authentication = webSecurityConfig!!.authenticationManager().authenticate(authToken)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun validateEmail(email: String): Boolean {
        val regexPattern = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$"
        val regex = Regex(regexPattern)
        return regex.matches(email)
    }
}