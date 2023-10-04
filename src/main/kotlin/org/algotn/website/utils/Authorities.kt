package org.algotn.website.utils

import org.algotn.website.auth.User

class Authorities {

    companion object {

        fun hasAdminPanelAuthority(user: User): Boolean {
            return user.authorities.contains("*") || user.authorities.contains("ADMIN_PANEL")
        }
    }
}