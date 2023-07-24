package org.algotn.website

import org.algotn.api.Chili
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication(scanBasePackages = ["org.algotn.website"])
open class WebApplication {

}

class WebApplicationKt {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Chili.getRedisInterface()
            if (args.isNotEmpty()) Chili.SAVE_LOCATION = "${args[0]}/algotn"
            SpringApplication.run(WebApplication::class.java, *args)
        }
    }
}

