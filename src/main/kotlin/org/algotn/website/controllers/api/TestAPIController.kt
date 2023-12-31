package org.algotn.website.controllers.api

import org.algotn.api.Chili
import org.algotn.api.tests.TestType
import org.algotn.website.auth.UserRepository
import org.algotn.website.auth.user.TNOAuth2User
import org.algotn.website.data.TestData
import org.algotn.website.listeners.WebSocketEventListener
import org.algotn.website.services.problem.ProblemLocationService
import org.algotn.website.services.tests.TestLocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import kotlin.math.min

@RestController
@RequestMapping("/api/tests")
class TestAPIController {

    @Autowired
    val fileLocationService: TestLocationService? = null

    @Autowired
    val problemLocationService: ProblemLocationService? = null

    @Autowired
    val userRepository: UserRepository? = null

    // User dependent
    @GetMapping("/all")
    fun getProblems(
        @RequestParam(required = false, defaultValue = "1", name = "page") page: Int,
        @RequestParam(required = false, defaultValue = "10", name = "count") count: Int,
        @RequestParam(required = false, defaultValue = "date.desc", name = "sort_by") sort: String
    ): Map<String, Any> {
        // FIXME: this doesn't work if user has a secret
        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val user = userRepository!!.findByUsername(username)
        if (!user.isPresent) {
            return mapOf("error" to "Authentication error")
        }

        val map = mutableMapOf<String, Any>()
        if (WebSocketEventListener.testsInProgress.containsKey(username)) {
            map["inProgress"] = WebSocketEventListener.testsInProgress[username]!!
        }

        val testData = Chili.getRedisInterface().getData(username, TestData::class.java, false)!!

        val maxPage = testData.allTests.size / count + 1

        var page = page
        if (page > maxPage) {
            page = maxPage
        } else if (page < 1) {
            page = 1
        }

        val consideredTests = testData.allTests.toMutableList()
        val total = consideredTests.size
        val sortParam = sort.split(".")[0]
        val sortType = sort.split(".")[1]
        val l =
            when (sortParam) {
                "date" -> {
                    if (sortType == "desc") {
                        consideredTests.sortedByDescending { it.timestamp }
                    } else {
                        consideredTests.sortedBy { it.timestamp }
                    }
                }

                "problem_name" -> {
                    if (sortType == "desc") {
                        consideredTests.sortedByDescending { it.problemName }
                    } else {
                        consideredTests.sortedBy { it.problemName }
                    }
                }

                else -> {
                    consideredTests
                }
            }
        map["completed"] = l.subList((page - 1) * count, min(page * count, l.size))
        map["total"] = total
        return map
    }

    // User dependent
    @GetMapping("/{id}")
    @ResponseBody
    fun getTestCode(@PathVariable id: String): ResponseEntity<*> {
        val principal = SecurityContextHolder.getContext().authentication.principal

        var username = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }

        if (principal is TNOAuth2User) {
            username = principal.getEmail()
        }

        val user = userRepository!!.findByUsername(username)
        if (user.isPresent) {
            val testData = Chili.getRedisInterface().getData(username, TestData::class.java)!!
            if (testData.testsIds.contains(id)) {
                val resource = fileLocationService!!.findInFileSystem(id)

                if (!resource.exists()) {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND)
                }

                val headers = org.springframework.http.HttpHeaders()
                headers.contentType = MediaType.APPLICATION_OCTET_STREAM

                return ResponseEntity(resource, headers, HttpStatus.OK)
            }
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    @PostMapping("/restricted/currentQueue")
    @ResponseBody
    fun getCurrentQueue(@RequestParam queueMap: Map<String, Int>): Map<String, Any> {
        queueMap.entries.forEach {
            val email = it.key
            val position = it.value

            // Send to websocket for user
        }
        return mapOf("error" to "Not implemented")
    }

    // Need secret to access url
    @GetMapping("/restricted/{id}")
    @ResponseBody
    fun getTestCodeSecret(@PathVariable id: String): ResponseEntity<*> {
        val resource = fileLocationService!!.findInFileSystem(id)

        if (!resource.exists()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val headers = org.springframework.http.HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM

        return ResponseEntity(resource, headers, HttpStatus.OK)
    }

    // Need secret to access url
    @GetMapping("/restricted/{problemSlug}/info")
    fun getTestInformation(@PathVariable problemSlug: String): Map<String, Any> {
        val problem = Chili.getProblems().getProblem(problemSlug) ?: return mapOf("error" to "Problem not found")

        val map = mutableMapOf<String, Any>()
        map["problemSlug"] = problemSlug
        map["problemName"] = problem.name

        val tests = arrayListOf<LinkedHashMap<String, Any>>()

        problem.secretFiles.secretFiles.forEach {
            val testMap = LinkedHashMap<String, Any>()
            testMap["name"] = it
            val type = problem.secretFiles.secretTestsTypes[it]!!
            testMap["type"] = type.name

            if (type == TestType.INPUT_OUTPUT) {
                testMap["inputURL"] = "/api/tests/restricted/$problemSlug/in/$it.in"
                testMap["outputURL"] = "/api/tests/restricted/$problemSlug/ans/$it.ans"
            }

            tests.add(testMap)
        }
        // TODO: only useful for numbers
        tests.sortBy {
            if (it["name"] is Int)
                it["name"] as Int
            else if (it["name"] is String)
                (it["name"] as String).toIntOrNull() ?: 0
            else
                0
        }
        map["tests"] = tests
        return map
    }

    // Need secret to access url
    @GetMapping("/restricted/{problemSlug}/in/{testName}.in")
    fun getTestInput(@PathVariable problemSlug: String, @PathVariable testName: String): ResponseEntity<*> {
        val problem = Chili.getProblems().getProblem(problemSlug) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        if (!problem.secretFiles.secretFiles.contains(testName)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val resource = problemLocationService!!.findSecretInFileSystem(problem.folderName, "$testName.in")

        val headers = org.springframework.http.HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM

        return ResponseEntity(resource, headers, HttpStatus.OK)
    }

    // Need secret to access url
    @GetMapping("/restricted/{problemSlug}/ans/{testName}.ans")
    fun getTestOutput(@PathVariable problemSlug: String, @PathVariable testName: String): ResponseEntity<*> {
        val problem = Chili.getProblems().getProblem(problemSlug) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        if (!problem.secretFiles.secretFiles.contains(testName)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val resource = problemLocationService!!.findSecretInFileSystem(problem.folderName, "$testName.ans")

        val headers = org.springframework.http.HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM

        return ResponseEntity(resource, headers, HttpStatus.OK)
    }
}