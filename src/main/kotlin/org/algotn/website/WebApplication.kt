package org.algotn.website

import org.algotn.api.Chili
import org.algotn.api.contest.Contest
import org.algotn.website.auth.User
import org.algotn.website.data.TestData
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication(scanBasePackages = ["org.algotn.website"])
open class WebApplication {

}

class WebApplicationKt {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Chili.getRedisInterface().registerData(User(), TestData())
            SpringApplication.run(WebApplication::class.java, *args)
        }
    }
}

