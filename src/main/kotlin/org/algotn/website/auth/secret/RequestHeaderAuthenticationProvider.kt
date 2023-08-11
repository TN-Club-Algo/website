package org.algotn.website.auth.secret

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

class RequestHeaderAuthenticationProvider : AuthenticationProvider {

    private val secret = "secret"

    override fun authenticate(authentication: Authentication?): Authentication {
        if (authentication == null) throw BadCredentialsException("No authentication object provided")
        if (authentication::class.java != PreAuthenticatedAuthenticationToken::class.java) throw BadCredentialsException(
            "Invalid authentication object"
        )

        val givenSecret = authentication.principal.toString()
        if (givenSecret != secret) throw BadCredentialsException("Invalid authentication secret")

        return PreAuthenticatedAuthenticationToken(authentication.principal, authentication.credentials, AuthorityUtils.createAuthorityList("SECRET"))
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }
}