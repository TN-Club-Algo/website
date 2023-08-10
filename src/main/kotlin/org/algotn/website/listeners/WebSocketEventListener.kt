package org.algotn.website.listeners

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.website.Test
import org.algotn.website.api.TestJSON
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

    companion object {
        // email -> test (a user can only have one test at a time)
        val testsInProgress = mutableMapOf<String, TestJSON>()
    }

    @Autowired
    private val template: SimpMessagingTemplate? = null
    private val gson = Gson()

    init {
        Chili.getRedisInterface().client.getTopic("pepper-inner-test-results", StringCodec())
            .addListener(String::class.java) { _, result ->
                val retMap = gson.fromJson(result, Map::class.java)

                val problem = Chili.getProblems().getProblem(retMap["problemSlug"].toString())
                if (problem != null) {
                    val testJson = TestJSON(
                        retMap["testID"].toString(),
                        retMap["email"].toString(),
                        problem,
                        retMap["codeURL"].toString(),
                        retMap["progress"].toString(),
                        retMap["timeElapsed"].toString(),
                        retMap["memoryUsed"].toString()
                    )

                    testsInProgress[testJson.email] = testJson

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