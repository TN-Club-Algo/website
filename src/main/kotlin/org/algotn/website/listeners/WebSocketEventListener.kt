package org.algotn.website.listeners

import com.google.gson.Gson
import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.website.api.TestJSON
import org.algotn.website.auth.Provider
import org.algotn.website.auth.UserRepository
import org.algotn.website.data.TestData
import org.algotn.website.utils.NotificationUtils
import org.redisson.client.codec.StringCodec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import java.util.*
import kotlin.jvm.optionals.getOrNull


@Component
@EnableScheduling
class WebSocketEventListener {

    companion object {
        // email -> test (a user can only have one test at a time)
        val testsInProgress = mutableMapOf<String, TestJSON>()
    }

    @Autowired
    private val template: SimpMessagingTemplate? = null

    @Autowired
    val userRepository: UserRepository? = null

    private val gson = Gson()

    private val token = System.getenv("WEBSOCKET_SECRET_TOKEN") ?: "secret_token"

    init {
        Chili.getRedisInterface().client.getTopic("pepper-test-results", StringCodec())
            .addListener(String::class.java) { _, result ->
                val retMap = gson.fromJson(result, Map::class.java)
                val email =
                    Chili.getRedisInterface().client.getMap<String, String>("test-to-user")[retMap["testID"] as String]!!
                val id = retMap["testID"].toString()
                val problem = Chili.getProblems().getProblem(retMap["problemSlug"].toString())
                val validated = retMap["result"] == "true"
                val info = retMap["info"].toString()
                if (problem != null) {
                    val testJson = TestJSON(
                        retMap["testID"].toString(),
                        -1,
                        email,
                        problem.slug,
                        problem.name,
                        "/api/tests/$id",
                        info,
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

                    if (validated && !problem.usersWhoSolved.contains(email)) {
                        problem.usersWhoSolved.add(email)
                        Chili.getRedisInterface().client.getSet<String>("problem-${problem.slug}-solved").add(email)

                        data.solvedProblems.add(problem.slug)

                        // List contests with this problem
                        val contests = Chili.getRedisInterface().getAllUUIDData(Contest::class.java).toMutableList()
                            .filter { it.problems.containsKey(problem.slug) }
                        contests.forEach {
                            it.updateUserScore(problem.slug, it.computeProblemScore(problem.slug), email)
                            it.problemSuccessCount[problem.slug] = (it.problemSuccessCount[problem.slug] ?: 0) + 1
                            Chili.getRedisInterface().client.getMap<String, Int>(it.successCountKey)[problem.slug] =
                                Chili.getRedisInterface().client.getMap<String, Int>(it.successCountKey)
                                    .getOrDefault(problem.slug, 0) + 1
                            Chili.getRedisInterface().saveData(it.uuid, it)
                        }

                        // Add problem awards
                        val user = userRepository!!.findByEmail(email).getOrNull()
                        if (user == null) {
                            Chili.logger.error("User with email=$email not found while adding awards")
                        } else {
                            if (problem.awards.isNotEmpty()) {
                                problem.awards.values.forEach {
                                    user.awards.add(it.clone())
                                }
                                if (user.provider == Provider.GOOGLE_TN) {
                                    NotificationUtils.sendNotificationToUser(
                                        template,
                                        user.id,
                                        "Vous avez reçu des récompenses pour avoir résolu le problème ${problem.name} !\nDirection votre profil pour les consulter.",
                                    )
                                } else {
                                    NotificationUtils.sendNotificationToUser(
                                        template,
                                        email,
                                        "Vous avez reçu des récompenses pour avoir résolu le problème ${problem.name} !\nDirection votre profil pour les consulter.",
                                    )
                                }
                                userRepository.save(user)
                            }
                        }
                    }
                    Chili.getRedisInterface().saveData(email, data)

                    val headerAccessor = SimpMessageHeaderAccessor.create()
                    headerAccessor.setHeader("Authorization", "Bearer $token")

                    val user = userRepository!!.findByEmail(email).getOrNull()
                    if (user == null) {
                        Chili.logger.error("User with email=$email not found while sending test result")
                    } else {
                        if (user.provider == Provider.GOOGLE_TN) {
                            this.template!!.convertAndSendToUser(
                                user.id,
                                "/queue/return/tests",
                                testJson,
                                headerAccessor.messageHeaders
                            )
                        } else {
                            this.template!!.convertAndSendToUser(
                                email,
                                "/queue/return/tests",
                                testJson,
                                headerAccessor.messageHeaders
                            )
                        }
                    }
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
                        data.testInProgress = null
                    } else {
                        testsInProgress[testJson.email] = testJson
                    }

                    Chili.getRedisInterface().saveData(email, data)

                    val headerAccessor = SimpMessageHeaderAccessor.create()
                    headerAccessor.setHeader("Authorization", "Bearer $token")

                    // FIXME
                    /*this.template!!.convertAndSendToUser(
                        email,
                        "/queue/return/tests",
                        testJson,
                        headerAccessor.messageHeaders
                    )*/
                }
            }
    }

    @EventListener
    fun handleSessionConnection(event: SessionConnectEvent) {
    }
}