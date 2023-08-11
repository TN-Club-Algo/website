package org.algotn.website.auth

import com.google.gson.Gson
import org.algotn.website.auth.secret.RequestHeaderAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.authentication.www.NonceExpiredException
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.servlet.handler.HandlerMappingIntrospector


@Configuration
@EnableWebSecurity
open class WebSecurityConfig {

    private var requestHeaderAuthenticationProvider: RequestHeaderAuthenticationProvider =
        RequestHeaderAuthenticationProvider()

    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return AuthenticationManager { authentication ->
            if (UserRepositoryImpl.doesUserMatchPassword(
                    authentication.name,
                    authentication.credentials.toString()
                )
            ) {
                UsernamePasswordAuthenticationToken(
                    authentication.name,
                    authentication.credentials,
                    emptyList()
                )
            } else {
                null
            }
        }
    }

    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean(name = ["mvcHandlerMappingIntrospector"])
    open fun mvcHandlerMappingIntrospector(): HandlerMappingIntrospector? {
        return HandlerMappingIntrospector()
    }

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/", "/error", "/blog", "/problem", "/scoreboard", "/problem/{slug}").permitAll()
                    .requestMatchers("/api/image/**").permitAll()
                    .requestMatchers("/register", "/login", "/password-reset", "/password-reset/{token}").anonymous()
                    .requestMatchers("/api/tests/restricted/**").hasAuthority("SECRET")
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it
                    .accessDeniedHandler { request, response, exception ->
                        response.sendRedirect(request.contextPath + "/")
                    }
            }
            .formLogin { login ->
                login
                    .loginPage("/login")
                    .failureHandler { request, response, exception ->
                        val responseMap = mutableMapOf<String, Any>()
                        responseMap["success"] = false

                        if (exception is AccountStatusException) {
                            responseMap["message"] = exception.message!!
                        } else if (exception is AuthenticationCredentialsNotFoundException) {
                            responseMap["message"] = exception.message!!
                        } else if (exception is AuthenticationServiceException) {
                            responseMap["message"] = "Erreur serveur lors de l'authentification. Code d'erreur : 1"
                        } else if (exception is BadCredentialsException) {
                            responseMap["message"] = "Le couple email / mot de passe est incorrect."
                        } else if (exception is InsufficientAuthenticationException) {
                            responseMap["message"] = "Erreur serveur lors de l'authentification. Code d'erreur : 2"
                        } else if (exception is NonceExpiredException) {
                            responseMap["message"] = "Erreur serveur lors de l'authentification. Code d'erreur : 3"
                        } else if (exception is PreAuthenticatedCredentialsNotFoundException) {
                            responseMap["message"] = exception.message!!
                        } else if (exception is ProviderNotFoundException) {
                            responseMap["message"] = "Erreur serveur lors de l'authentification. Code d'erreur : 4"
                        } else if (exception is RememberMeAuthenticationException) {
                            responseMap["message"] = "Erreur serveur lors de l'authentification. Code d'erreur : 5"
                        } else if (exception is SessionAuthenticationException) {
                            responseMap["message"] =
                                "Erreur lors de la création de la session. Avez-vous trop de sessions simultanées ?"
                        } else if (exception is UsernameNotFoundException) {
                            responseMap["message"] = "Le couple email / mot de passe est incorrect."
                        } else {
                            responseMap["message"] = "Le couple email / mot de passe est incorrect."
                        }

                        // write the response
                        response.contentType = "application/json"
                        response.writer.write(Gson().toJson(responseMap))
                    }
                    .successHandler { request, response, authentication ->
                        val responseMap = mutableMapOf<String, Any>()
                        responseMap["success"] = true
                        responseMap["redirect"] = "/"

                        response.contentType = "application/json"
                        response.writer.write(Gson().toJson(responseMap))
                    }
                    .permitAll(false)
            }
            .logout { logout ->
                logout.permitAll()
            }

        http.addFilterAfter(requestHeaderAuthenticationFilter(http), BasicAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    open fun requestHeaderAuthenticationFilter(http: HttpSecurity): RequestHeaderAuthenticationFilter {
        val filter = RequestHeaderAuthenticationFilter()
        filter.setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/api/tests/{problemSlug}/**", "GET"))
        filter.setPrincipalRequestHeader("x-auth-secret-key")
        filter.setExceptionIfHeaderMissing(false)
        filter.setAuthenticationManager(
            http.getSharedObject(AuthenticationManagerBuilder::class.java)
                .authenticationProvider(requestHeaderAuthenticationProvider).build()
        )
        return filter
    }
}