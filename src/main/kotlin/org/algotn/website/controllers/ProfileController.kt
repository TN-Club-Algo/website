package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.website.auth.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
class ProfileController {

    private val passwordEncoder = BCryptPasswordEncoder()

    @Autowired
    val userRepository: UserRepository? = null

    @Autowired
    val authenticationManager: AuthenticationManager? = null

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

    @PostMapping("/profile/info")
    @ResponseBody
    fun changeUserInformation(
        @RequestParam("firstName", defaultValue = "") firstName: String,
        @RequestParam("lastName", defaultValue = "") lastName: String,
        @RequestParam("nickname", defaultValue = "") nickname: String,
        @RequestParam("preferNickname", defaultValue = "true") preferNickname: String
    ): Map<String, Any> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        val user = userRepository!!.findByUsername(username)
        if (!user.isPresent) {
            return mapOf("error" to "You are not logged in", "success" to false)
        }

        val u = user.get()
        u.firstName = firstName
        u.lastName = lastName

        val nicknameMap = Chili.getRedisInterface().client.getMap<String, String>("user-nicknames")
        if (nicknameMap.containsKey(nickname) && nicknameMap[nickname] != u.email) {
            return mapOf("error" to "Le surnom choisi est déjà pris", "success" to false)
        }
        u.nickname = nickname

        u.preferNickname = preferNickname == "true"

        userRepository.save(u)
        return mapOf("success" to true)
    }

    @PostMapping("/profile/password")
    @ResponseBody
    fun changePassword(
        @RequestParam("oldPassword", defaultValue = "") oldPassword: String,
        @RequestParam("newPassword", defaultValue = "") newPassword: String,
        @RequestParam("newPasswordConfirm", defaultValue = "") newPasswordConfirm: String
    ): Map<String, Any> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        val user = userRepository!!.findByUsername(username)
        if (!user.isPresent) {
            return mapOf("error" to "You are not logged in", "success" to false)
        }

        val u = user.get()
        if (!passwordEncoder.matches(oldPassword, u.password)) {
            return mapOf("error" to "L'ancien mot de passe est incorrect", "success" to false)
        }

        if (newPassword != newPasswordConfirm) {
            return mapOf("error" to "Les mots de passe ne correspondent pas", "success" to false)
        }

        u.password = passwordEncoder.encode(newPassword)
        userRepository.save(u)

        val request = UsernamePasswordAuthenticationToken(username, newPassword)
        SecurityContextHolder.getContext().authentication = authenticationManager!!.authenticate(request)

        return mapOf("success" to true)
    }

    // TODO: si l'utilisateur doit attendre une VM, lui dire qu'il est en file d'attente et envoyer via le websocket à chaque fois qu'il y a un changement
    // TODO: move this mapping

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