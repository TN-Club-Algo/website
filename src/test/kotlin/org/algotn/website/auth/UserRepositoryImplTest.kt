package org.algotn.website.auth

import com.redis.testcontainers.RedisContainer
import org.algotn.api.Chili
// import org.algotn.api.config.ConfigValues
import org.algotn.api.database.RedisDatabase
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import java.nio.file.Files
import java.nio.file.Path

class UserRepositoryImplTest {

    companion object {

        @Container
        private val container = RedisContainer(DockerImageName.parse("redis:7.0.9-alpine")).withExposedPorts(6379)

        private lateinit var redisDatabase: RedisDatabase

        init {
            container.start()
        }

        @JvmStatic
        @BeforeAll
        fun createRedis(@TempDir tempDir: Path) {
            val configFile = tempDir.resolve("config.yml")
            Files.write(
                configFile, listOf(
                    "redis:",
                    "  enabled: true",
                    "  uri: \"${container.redisURI}\"",
                    "  password: \"\"",
                    "  database: 0"
                )
            )

            // ConfigValues.editConfig(configFile)

            redisDatabase = RedisDatabase()
            redisDatabase.connect()
        }
    }

    @Test
    fun testData() {
        assert(Chili.getRedisInterface().getData("example@example.com", TestUserData::class.java) == null)

        val data = TestUserData("example@example.com")
        val content = data.exampleData
        Chili.getRedisInterface().saveData(data.email, data)

        val recovered = Chili.getRedisInterface().getData("example@example.com", TestUserData::class.java)

        assert(recovered != null)
        assert(recovered!!.exampleData == content)
    }

    @Test
    fun saveAndRemoveUser() {
        val user = User()
        UserRepositoryImpl().save(user)
        Chili.getRedisInterface().saveData("users:${user.email}", user)

        assert(Chili.getRedisInterface().getData("users:${user.email}", TestUserData::class.java) != null)
        assert(Chili.getRedisInterface().getData("users:${user.email}", TestUserData::class.java)!!.email == user.email)

        UserRepositoryImpl().removeUser(user.email)

        assert(Chili.getRedisInterface().getData("users:${user.email}", TestUserData::class.java) == null)
    }
}