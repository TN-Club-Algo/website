package org.algotn.website.controllers

import org.algotn.website.Test

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class TestController {

    @MessageMapping("/results")
    @SendTo("/return/tests")
    fun sendTest(message: Test): Test {
        return message;
    }
}