package org.algotn.api

import org.algotn.api.database.RedisDatabase
import org.algotn.api.database.RedisInterface
import org.algotn.api.problem.Problems
import org.algotn.api.problem.validator.Validators
import org.apache.commons.lang3.SystemUtils
import java.io.File

class Chili private constructor() {

    private val redisInterface: RedisInterface
    val validators = Validators()

    companion object {
        val logger = org.slf4j.LoggerFactory.getLogger(Chili::class.java)!!

        var SAVE_LOCATION = if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC_OSX) "/etc/algotn" else "C:/algotn"

        private var chili: Chili? = null
        private var problems: Problems? = null

        private fun load() {
            if (chili == null) {
                File(SAVE_LOCATION).mkdirs()

                chili = Chili()
                problems = Problems()
            } else {
                throw IllegalAccessException()
            }
        }

        fun get(): Chili {
            if (chili == null) load()
            return chili!!
        }

        fun getRedisInterface(): RedisInterface {
            if (chili == null) load()
            return chili!!.redisInterface
        }

        fun getProblems(): Problems {
            if (chili == null) load()
            return problems!!
        }
    }

    init {
        val database = RedisDatabase().connect()
        redisInterface = RedisInterface(database.redissonClient)

        Runtime.getRuntime().addShutdownHook(Thread {
            redisInterface.saveAllCachedData()
        })
    }
}