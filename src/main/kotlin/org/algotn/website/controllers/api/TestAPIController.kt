package org.algotn.website.controllers.api

import org.algotn.api.Chili
import org.algotn.website.api.TestJSON
import org.algotn.website.auth.UserRepository
import org.algotn.website.data.TestData
import org.algotn.website.listeners.WebSocketEventListener
import org.algotn.website.services.problem.ProblemLocationService
import org.algotn.website.services.tests.TestLocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

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
    fun getProblems(principal: Principal): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        if (WebSocketEventListener.testsInProgress.containsKey(principal.name)) {
            map["inProgress"] = WebSocketEventListener.testsInProgress[principal.name]!!
        }
        map["completed"] = mutableMapOf<String, TestJSON>()
        return map
    }

    // User dependent
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

    // Need secret to access url
    @GetMapping("/{problemSlug}/info")
    fun getTestInformation(@PathVariable problemSlug: String): Map<String, Any> {
        val problem = Chili.getProblems().getProblem(problemSlug) ?: return mapOf("error" to "Problem not found")

        val map = mutableMapOf<String, Any>()
        map["problemSlug"] = problemSlug
        map["problemName"] = problem.name
        map["testNames"] = problem.secretFiles.secretFiles
        return map
    }

    // Need secret to access url
    @GetMapping("/{problemSlug}/in/{testName}.in")
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
    @GetMapping("/{problemSlug}/ans/{testName}.ans")
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

    @MessageMapping("/results")
    @SendTo("/return")
    fun sendTest(message: TestJSON): TestJSON {
        return message;
    }
}