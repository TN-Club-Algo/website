package org.algotn.website.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class BlogController {

    @RequestMapping("/blog")
    fun blog(): String {
        return "/blog/blog"
    }

    @RequestMapping("/blog/create")
    fun create(): String {
        return "/blog/create"
    }

    @RequestMapping("/blog/article/{artNumber}")
    fun article(): String {
        return "/blog/article"
    }
}