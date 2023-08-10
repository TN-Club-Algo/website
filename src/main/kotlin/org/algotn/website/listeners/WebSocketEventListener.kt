package org.algotn.website.listeners

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.website.api.TestJSON
import org.algotn.website.data.TestData
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
        Chili.getRedisInterface().client.getTopic("pepper-test-results", StringCodec())
            .addListener(String::class.java) { _, result ->
                val retMap = gson.fromJson(result, Map::class.java)
                val email = retMap["email"].toString()
                val problem = Chili.getProblems().getProblem(retMap["problemSlug"].toString())
                if (problem != null) {
                    val testJson = TestJSON(
                        retMap["testID"].toString(),
                        email,
                        problem,
                        retMap["codeURL"].toString(),
                        retMap["progress"].toString(),
                        retMap["timeElapsed"].toString(),
                        retMap["memoryUsed"].toString()
                    )

                    testsInProgress[testJson.email] = testJson

                    // save for user
                    val data = Chili.getRedisInterface().getData(email, TestData::class.java)
                    data!!.testInProgress = null

                    data.allTests.add(testJson)
                    Chili.getRedisInterface().saveData(email, data)

                    this.template!!.convertAndSend("/return/tests", testJson)
                }
            }

        Chili.getRedisInterface().client.getTopic("pepper-inner-test-results", StringCodec())
            .addListener(String::class.java) { _, result ->
                val retMap = gson.fromJson(result, Map::class.java)

                val email = retMap["email"].toString()
                val problem = Chili.getProblems().getProblem(retMap["problemSlug"].toString())
                if (problem != null) {
                    val testJson = TestJSON(
                        retMap["testID"].toString(),
                        email,
                        problem,
                        retMap["codeURL"].toString(),
                        retMap["progress"].toString(),
                        retMap["timeElapsed"].toString(),
                        retMap["memoryUsed"].toString()
                    )

                    testsInProgress[testJson.email] = testJson

                    // save for user
                    val data = Chili.getRedisInterface().getData(email, TestData::class.java)
                    data!!.testInProgress = testJson

                    Chili.getRedisInterface().saveData(email, data)

                    this.template!!.convertAndSend("/return/tests", testJson)
                }
            }
    }

    @EventListener
    fun handleSessionConnection(event: SessionConnectEvent) {
    }
}