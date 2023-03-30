package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class SuperAdmin {

    @RequestMapping("/modoSuperAdmin")
    fun modoSuperAdmin(): String {
        return "sadmin/modoSuperAdmin"
    }

    @RequestMapping("/servUse")
    fun servUse(): String {
        return "sadmin/modoSuperAdmin"
    }
}