package org.algotn.website.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
open class WebSecurityConfig {

    companion object {
        fun authenticationManager(): AuthenticationManager {
            return AuthenticationManager { authentication ->
                if (UserRepositoryImpl.doesUserMatchPassword(authentication.name, authentication.credentials.toString())) {
                    UsernamePasswordAuthenticationToken(authentication.name, authentication.credentials, emptyList())
                } else {
                    null
                }
            }
        }
    }

    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/register").anonymous()
                    .requestMatchers("/password-reset").anonymous()
                    .requestMatchers("/password-reset/{token}").anonymous()
                    .anyRequest().authenticated()
            }
            .formLogin { login ->
                login
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
            }
            .logout { logout ->
                logout.permitAll()
            }
        return http.build()
    }
}