package org.algotn.website.controllers.admin

import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.user.TNOAuth2User
import org.algotn.website.utils.Authorities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class AdminController {

    @Autowired
    private val userRepository: UserRepository? = null

    @GetMapping("/admin")
    fun adminPanel(model: Model) : ModelAndView{
        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }
        val user = userRepository!!.findByEmail(username)

        if (user.isPresent && Authorities.hasAdminPanelAuthority(user.get())) {
            model.addAttribute("user", user.get())
            return ModelAndView("admin/panel")
        } else {
            throw AccessDeniedException("")
        }
    }

    @GetMapping("/admin/users")
    fun adminPanelUsers(model: Model) : ModelAndView{
        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }
        val user = userRepository!!.findByEmail(username)

        if (user.isPresent && Authorities.hasAdminPanelAuthority(user.get())) {
            model.addAttribute("user", user.get())
            return ModelAndView("admin/panelUsers")
        } else {
            throw AccessDeniedException("")
        }
    }
}