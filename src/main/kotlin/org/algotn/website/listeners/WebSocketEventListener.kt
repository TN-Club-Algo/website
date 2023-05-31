package org.algotn.website.listeners

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.website.Test
import org.redisson.client.codec.StringCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent


@Component
@EnableScheduling
class WebSocketEventListener {

    @Autowired
    private val template: SimpMessagingTemplate? = null

    init {
        Chili.getRedisInterface().client.getTopic("pepper-inner-test-results", StringCodec())
            .addListener(String::class.java) { cs, result ->
                println(result)
                val retMap = Gson().fromJson(result, Map::class.java)

                this.template!!.convertAndSend(
                    "/return/tests",
                    Test(
                        retMap["problemName"].toString(),
                        retMap["index"].toString().toFloat().toInt(),
                        retMap["answer"].toString(),
                        retMap["ok"].toString().toBoolean()
                    )
                )
            }
    }

    @EventListener
    fun handleSessionConnection(event: SessionConnectEvent) {
        println("connected")
    }

    /*@Scheduled(fixedRate = 2000)
    fun fireGreeting() {
        println("yo")
        this.template!!.convertAndSend(
            "/return/tests",
            Test(
                "Fire", ThreadLocalRandom.current().nextInt(0, 123),
                "aefzhek:", Random().nextBoolean()
            )
        )
    }*/

}