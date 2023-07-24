package org.algotn.website

import org.algotn.api.Chili
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.File


@SpringBootApplication(scanBasePackages = ["org.algotn.website"])
open class WebApplication {

}

class WebApplicationKt {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Chili.getRedisInterface()
            SpringApplication.run(WebApplication::class.java, *args)
        }
    }
}

