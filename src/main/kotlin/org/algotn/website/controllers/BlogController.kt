package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BlogController {

    @GetMapping("/blog")
    fun blog(): String {
        return "/blog/blog"
    }

    @GetMapping("/blog/create")
    fun create(): String {
        return "/blog/create"
    }

    @GetMapping("/blog/article/{artNumber}")
    fun article(): String {
        return "/blog/article"
    }
}