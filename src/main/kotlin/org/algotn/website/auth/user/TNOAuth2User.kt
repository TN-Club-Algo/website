package org.algotn.website.auth.user

import org.algotn.api.utils.slugify
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class TNOAuth2User(private val oauth2User: OAuth2User) : OAuth2User {

    fun getFirstName(): String {
        return oauth2User.getAttribute("given_name")!!
    }

    fun getLastName(): String {
        return oauth2User.getAttribute("family_name")!!
    }

    fun getEmail(): String {
        return oauth2User.getAttribute("email")!!
    }

    override fun getName(): String {
        return (oauth2User.getAttribute("name") as String).slugify()
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return oauth2User.attributes;
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return oauth2User.authorities
    }

    override fun toString(): String {
        return "TNOAuth2User(email=${getEmail()}, firstName=${getFirstName()}, lastName=${getLastName()})"
    }
}