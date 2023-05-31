package org.algotn.website.listeners

import org.algotn.website.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import java.util.*
import java.util.concurrent.ThreadLocalRandom


@Component
@EnableScheduling
class WebSocketEventListener {

    @Autowired
    private val template: SimpMessagingTemplate? = null

    @EventListener
    fun handleSessionConnection(event: SessionConnectEvent) {
        println("connected")
    }

    @Scheduled(fixedRate = 2000)
    fun fireGreeting() {
        println("yo")
        this.template!!.convertAndSend(
            "/return/tests",
            Test(
                "Fire", ThreadLocalRandom.current().nextInt(0, 123),
                "aefzhek:", Random().nextBoolean()
            )
        )
    }
}