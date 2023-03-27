package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class BlogController {

    @RequestMapping("/blog")
    fun blog(): Stirng {
        return "/blog"
    }

    @RequestMapping("/blog/create")
    fun create(): String {
        return "/blog/create"
    }

    @RequestMapping("/blog/{artNumber}")
    fun article(): String {
        return "/blog/{artNumner}"
    }
}