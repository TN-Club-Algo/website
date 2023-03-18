package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class DefaultController {

    @RequestMapping("/")
    fun index(): String {
        return "index"
    }
}