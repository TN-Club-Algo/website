package org.algotn.website.auth

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.website.auth.secret.RequestHeaderAuthenticationProvider
import org.algotn.website.auth.user.TNOAuth2User
import org.algotn.website.services.auth.TNOAuth2UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
@Order(SecurityProperties.BASIC_AUTH_ORDER)
open class WebSecurityConfig {

    private val gson = Gson()

    private var requestHeaderAuthenticationProvider: RequestHeaderAuthenticationProvider =
        RequestHeaderAuthenticationProvider()

    @Autowired
    private val oauthUserService: TNOAuth2UserService? = null

    @Bean
    open fun authenticationManager(): AuthenticationManager {
        return AuthenticationManager { authentication ->
            val pair = UserRepositoryImpl.doesUserMatchPassword(
                authentication.name,
                authentication.credentials.toString()
            )
            if (pair.first) {
                UsernamePasswordAuthenticationToken(
                    authentication.name,
                    authentication.credentials,
                    pair.second.map { SimpleGrantedAuthority(it) }
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
                    .requestMatchers(
                        "/",
                        "/error",
                        "/blog",
                        "/problem",
                        "/scoreboard",
                        "/problem/{slug}",
                        "/contest",
                        "/contest/{uuid}",
                        "/contest/leaderboard/{uuid}",
                        "/oauth2/**",
                    )
                    .permitAll()
                    .requestMatchers("/static/**", "/api/image/**").permitAll()
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
                        response.writer.write(gson.toJson(responseMap))
                    }
                    .successHandler { request, response, authentication ->
                        val responseMap = mutableMapOf<String, Any>()
                        responseMap["success"] = true
                        responseMap["redirect"] = "/"

                        response.contentType = "application/json"
                        response.writer.write(gson.toJson(responseMap))
                    }
                    .permitAll(false)
            }
            .oauth2Login { oauth2 ->
                oauth2.loginPage("/login")
                oauth2.userInfoEndpoint {
                    it.userService(oauthUserService)
                }
                oauth2.successHandler { request, response, authentication ->
                    val oauthUser = authentication.principal as TNOAuth2User
                    oauthUserService!!.processOAuthPostLogin(oauthUser)
                    response.sendRedirect("/")
                }
                oauth2.failureHandler { request, response, exception ->
                    Chili.logger.error("OAuth2 login failed", exception)
                }
            }
            .logout { logout ->
                logout.permitAll()
            }
            .headers {
                it.contentSecurityPolicy { it2 ->
                    it2.policyDirectives("upgrade-insecure-requests")
                }
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