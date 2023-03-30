package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ProfileController {

    @RequestMapping("/profile")
    fun profile(): String {
        return "profiles/profile"
    }

    @RequestMapping("/profile/info")
    fun info(): String {
        return "profiles/info"
    }

    @RequestMapping("/profile/parameter")
    fun parameter(): String {
        return "profiles/parameter"
    }
}