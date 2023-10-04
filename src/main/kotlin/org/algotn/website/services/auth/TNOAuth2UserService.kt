package org.algotn.website.services.auth

import org.algotn.api.Chili
import org.algotn.api.utils.slugify
import org.algotn.website.auth.Provider
import org.algotn.website.auth.User
import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.user.TNOAuth2User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class TNOAuth2UserService : DefaultOAuth2UserService() {

    @Autowired
    private val userRepository: UserRepository? = null

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val user: OAuth2User = super.loadUser(userRequest)
        return TNOAuth2User(user)
    }

    fun processOAuthPostLogin(tnOAuth2User: TNOAuth2User) {
        val userOpt = userRepository!!.findByEmail(tnOAuth2User.getEmail())
        if (userOpt.isEmpty) {
            val user = User()
            user.firstName = tnOAuth2User.getFirstName()
            user.lastName = tnOAuth2User.getLastName()
            user.email = tnOAuth2User.getEmail()

            user.provider = Provider.GOOGLE_TN
            user.id = tnOAuth2User.name.slugify()

            val emailMap = Chili.getRedisInterface().client.getMap<String, String>("user-emails")
            val nicknameMap = Chili.getRedisInterface().client.getMap<String, String>("user-nicknames")

            nicknameMap[user.nickname] = user.email
            emailMap[user.email] = user.nickname

            userRepository.save(user)
        }
    }
}