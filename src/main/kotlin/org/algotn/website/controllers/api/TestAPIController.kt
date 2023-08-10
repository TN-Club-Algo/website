package org.algotn.website.controllers.api

import org.algotn.website.api.TestJSON
import org.algotn.website.listeners.WebSocketEventListener
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tests")
class TestAPIController {

    @GetMapping("/all")
    fun getProblems(principal: Principal): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        if (WebSocketEventListener.testsInProgress.containsKey(principal.name)) {
            map["inProgress"] = WebSocketEventListener.testsInProgress[principal.name]!!
        }
        map["completed"] = mutableMapOf<String, TestJSON>()
        return map
    }

    @MessageMapping("/results")
    @SendTo("/return")
    fun sendTest(message: TestJSON): TestJSON {
        return message;
    }
}