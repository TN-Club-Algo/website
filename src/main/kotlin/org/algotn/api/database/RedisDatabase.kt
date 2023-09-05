package org.algotn.api.database

import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator
import com.fasterxml.jackson.databind.module.SimpleModule
import org.algotn.api.database.serializer.ElySerializer
import org.algotn.api.database.serializer.base.EnumDeserializer
import org.algotn.api.database.serializer.base.EnumSerializer
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.JsonJacksonCodec
import org.redisson.config.Config

class RedisDatabase {

    lateinit var redissonClient: RedissonClient
        private set

    fun connect(): RedisDatabase {
        val host = System.getenv("REDIS_ADDRESS") ?: "redis://localhost"
        val port = (System.getenv("REDIS_PORT") ?: "6379").toInt()
        val password = System.getenv("REDIS_PASS") ?: ""
        val databaseNumber = (System.getenv("REDIS_DATABASE") ?: "0").toInt()

        val address = host.ifEmpty { "redis://localhost" } + ":" + port

        val codec = JsonJacksonCodec()
        val config = Config()

        val ptv: PolymorphicTypeValidator = BasicPolymorphicTypeValidator.builder().build()
        codec.objectMapper.activateDefaultTyping(ptv, DefaultTyping.NON_FINAL)
        // codec.objectMapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class")

        for (module in ElySerializer.MODULES) {
            codec.objectMapper.registerModule(module)
        }

        val simpleModule = SimpleModule()
        simpleModule.addSerializer(Enum::class.java, EnumSerializer())
        simpleModule.addDeserializer(Enum::class.java, EnumDeserializer())
        codec.objectMapper.registerModule(simpleModule)

        config.codec = codec
        config.useSingleServer().setAddress(address)
            .setConnectionMinimumIdleSize(1)
            .setSubscriptionConnectionPoolSize(128)
            .setDatabase(databaseNumber)
            .setPassword(if (password == "") null else password)
            .setRetryAttempts(3).retryInterval = 1500

        redissonClient = Redisson.create(config)
        return this
    }

    fun disconnect() {
        if (this::redissonClient.isInitialized) {
            redissonClient.shutdown()
        }
    }
}