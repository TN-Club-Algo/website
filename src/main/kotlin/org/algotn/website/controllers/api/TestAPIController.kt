package org.algotn.website.controllers.api

import org.algotn.api.Chili
import org.algotn.website.api.TestJSON
import org.algotn.website.auth.UserRepository
import org.algotn.website.data.TestData
import org.algotn.website.listeners.WebSocketEventListener
import org.algotn.website.services.tests.TestLocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal
import org.springframework.core.io.FileSystemResource
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tests")
class TestAPIController {

    @Autowired
    val fileLocationService: TestLocationService? = null

    @Autowired
    val userRepository: UserRepository? = null

    @GetMapping("/all")
    fun getProblems(principal: Principal): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        if (WebSocketEventListener.testsInProgress.containsKey(principal.name)) {
            map["inProgress"] = WebSocketEventListener.testsInProgress[principal.name]!!
        }
        map["completed"] = mutableMapOf<String, TestJSON>()
        return map
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun getTestCode(principal: Principal, @PathVariable id: String): FileSystemResource {
        val user = userRepository!!.findByUsername(principal.name)
        if (user.isPresent) {
            val testData = Chili.getRedisInterface().getData(principal.name, TestData::class.java)!!
            if (testData.testsIds.contains(id)) {
                return fileLocationService!!.findInFileSystem(id)
            }
        }
        return FileSystemResource("")
    }

    @MessageMapping("/results")
    @SendTo("/return")
    fun sendTest(message: TestJSON): TestJSON {
        return message;
    }
}