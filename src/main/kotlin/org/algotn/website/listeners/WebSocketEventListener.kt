package org.algotn.website.listeners

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.website.api.TestJSON
import org.algotn.website.data.TestData
import org.redisson.client.codec.StringCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import java.util.*


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

    private val token = "token"

    init {
        Chili.getRedisInterface().client.getTopic("pepper-test-results", StringCodec())
            .addListener(String::class.java) { _, result ->
                val retMap = gson.fromJson(result, Map::class.java)
                val email =
                    Chili.getRedisInterface().client.getMap<String, String>("test-to-user")[retMap["testID"] as String]!!
                val id = retMap["testID"].toString()
                val problem = Chili.getProblems().getProblem(retMap["problemSlug"].toString())
                val testResult = retMap["result"].toString()
                val validated = retMap["result"] == "true"
                if (problem != null) {
                    val testJson = TestJSON(
                        retMap["testID"].toString(),
                        -1,
                        email,
                        problem.slug,
                        problem.name,
                        "/api/tests/$id",
                        testResult,
                        "none",
                        "none",
                        ended = true,
                        validated = validated,
                        timestamp = Date().time
                    )

                    testsInProgress.remove(testJson.email)

                    // save for user
                    val data = Chili.getRedisInterface().getData(email, TestData::class.java, true)
                    data!!.testInProgress = null

                    data.allTests.add(testJson)
                    Chili.getRedisInterface().saveData(email, data)

                    if (validated) {
                        problem.usersWhoSolved.add(email)
                        Chili.getRedisInterface().client.getSet<String>("problem-${problem.slug}-solved").add(email)
                    }

                    val headerAccessor = SimpMessageHeaderAccessor.create()
                    headerAccessor.setHeader("Authorization", "Bearer $token")

                    this.template!!.convertAndSendToUser(
                        email,
                        "/queue/return/tests",
                        testJson,
                        headerAccessor.messageHeaders
                    )
                }
            }

        Chili.getRedisInterface().client.getTopic("pepper-inner-test-results", StringCodec())
            .addListener(String::class.java) { _, result ->
                val retMap = gson.fromJson(result, Map::class.java)

                val email =
                    Chili.getRedisInterface().client.getMap<String, String>("test-to-user")[retMap["testID"] as String]!!
                val id = retMap["testID"] as String
                val index = (retMap["index"] as Double).toInt()
                val problem = Chili.getProblems().getProblem(retMap["problemSlug"] as String)
                val progress = retMap["result"] as String
                if (problem != null) {
                    val testJson = TestJSON(
                        id,
                        index,
                        email,
                        problem.slug,
                        problem.name,
                        "/api/tests/$id",
                        progress,
                        "none",
                        "none"
                        //retMap["timeElapsed"].toString(),
                        //retMap["memoryUsed"].toString()
                    )

                    // save for user
                    val data = Chili.getRedisInterface().getData(email, TestData::class.java, true)
                    data!!.testInProgress = testJson

                    if (data.allTests.contains(testJson)) {
                        // Looks like the test was already completed
                        testsInProgress.remove(testJson.email)
                    } else {
                        testsInProgress[testJson.email] = testJson
                    }

                    Chili.getRedisInterface().saveData(email, data)

                    val headerAccessor = SimpMessageHeaderAccessor.create()
                    headerAccessor.setHeader("Authorization", "Bearer $token")

                    this.template!!.convertAndSendToUser(
                        email,
                        "/queue/return/tests",
                        testJson,
                        headerAccessor.messageHeaders
                    )
                }
            }
    }

    @EventListener
    fun handleSessionConnection(event: SessionConnectEvent) {
    }
}