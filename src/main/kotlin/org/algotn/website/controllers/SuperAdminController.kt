package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SuperAdminController {

    @GetMapping("/modoSuperAdmin")
    fun modoSuperAdmin(): String {
        return "sadmin/modoSuperAdmin"
    }

    @GetMapping("/servUse")
    fun servUse(): String {
        return "sadmin/modoSuperAdmin"
    }
}