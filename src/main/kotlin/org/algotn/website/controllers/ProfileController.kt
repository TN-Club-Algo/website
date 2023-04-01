package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProfileController {

    @GetMapping("/profile")
    fun profile(): String {
        return "profiles/profile"
    }

    @GetMapping("/profile/info")
    fun info(): String {
        return "profiles/info"
    }

    @GetMapping("/profile/parameter")
    fun parameter(): String {
        return "profiles/parameter"
    }
}