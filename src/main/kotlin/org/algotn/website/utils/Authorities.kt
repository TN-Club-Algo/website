package org.algotn.website.utils

import org.algotn.website.auth.User
import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.user.TNOAuth2User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

class Authorities {

    companion object {

        fun hasAdminPanelAuthority(user: User): Boolean {
            return user.authorities.contains("*") || user.authorities.contains("ADMIN_PANEL")
        }

        fun hasUsersAuthority(user: User): Boolean {
            return user.authorities.contains("*") || user.authorities.contains("USERS")
        }

        fun getCurrentUser(userRepository: UserRepository): User? {
            val principal = SecurityContextHolder.getContext().authentication.principal

            var username = if (principal is UserDetails) {
                principal.username
            } else {
                principal.toString()
            }

            if (principal is TNOAuth2User) {
                username = principal.getEmail()
            }

            return userRepository.findByEmail(username).orElse(null)
        }
    }
}