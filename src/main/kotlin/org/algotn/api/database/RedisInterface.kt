package org.algotn.api.database

import org.algotn.api.database.data.Data
import org.redisson.api.RedissonClient

class RedisInterface(val client: RedissonClient) {

    private val keyPerData: HashMap<String, String> = HashMap()

    private val cachedData: HashMap<String, HashMap<Class<out Data>, Data>> = HashMap()

    init {
        // Save all data every minute
        val executor = java.util.concurrent.Executors.newSingleThreadScheduledExecutor()
        executor.scheduleAtFixedRate({
            saveAllCachedData()
        }, 0, 1, java.util.concurrent.TimeUnit.MINUTES)
    }

    fun client(): RedissonClient {
        return client
    }

    fun saveAllCachedData() {
        if (cachedData.isNotEmpty()) {
            for (email in cachedData.keys) {
                for (clazz in cachedData[email]!!.keys) {
                    saveData(email, cachedData[email]!![clazz]!!)
                }
            }
        }
    }

    fun removeAllData(email: String) {
        client.keys.getKeysByPattern("users:${email}:*").forEach {
            client.getMap<String, Any>(it).clear()
        }
    }

    fun removeData(identifier: String, clazz: Class<out Data>) {
        client.getMap<String, Any>("${identifier}:${clazz.name}").clear()
    }

    fun exists(identifier: String): Boolean {
        return client.getMap<String, Any>(identifier).isNotEmpty()
    }

    fun saveData(email: String?, data: Data) {
        val map = client.getMap<Any, Any?>(data.getFullKey(email))
        data.toMap().forEach {
            if (it.value == null) map.remove(it.key)
            else map[it.key] = it.value
        }
    }

    fun saveData(data: Data) {
        val map = client.getMap<Any, Any?>(data.getFullKey(null))
        data.toMap().forEach {
            if (it.value == null) map.remove(it.key)
            else map[it.key] = it.value
        }
    }

    fun hasData(email: String?, clazz: Class<out Data>): Boolean {
        if (email == null) {
            return client.getMap<Any, Any>(keyPerData[clazz.name]!!).isNotEmpty()
        }
        return client.getMap<Any, Any>(keyPerData[clazz.name]!!.replace("uuid", email)).isNotEmpty()
    }

    fun <T : Data> getData(clazz: Class<T>): T? {
        return getData(null, clazz)
    }

    fun <T : Data> getData(email: String?, clazz: Class<T>, updateCache: Boolean = false): T? {
        if (email == null) {
            return Data.fromMap(client.getMap(keyPerData[clazz.name]!!), clazz)
        }

        if (!updateCache && cachedData.containsKey(email) && cachedData[email]!!.containsKey(clazz)) return cachedData[email]!![clazz] as T?
        val createdData: T = Data.fromMap(
            client.getMap(
                keyPerData[clazz.name]!!.replace("uuid", email)
            ), clazz
        )
        if (!cachedData.containsKey(email)) cachedData[email] = java.util.HashMap<Class<out Data>, Data>()
        cachedData[email]!![clazz] = createdData
        return createdData
    }

    fun <T : Data> getAllUUIDData(clazz: Class<T>): List<T> {
        return client.keys.getKeysByPattern("${clazz.simpleName.lowercase()}:*").map {
            Data.fromMap(client.getMap(it), clazz)
        }
    }

    fun getAllUsers(): List<org.algotn.website.auth.User> {
        return client.keys.getKeysByPattern("users:*").map {
            Data.fromMap(client.getMap(it), org.algotn.website.auth.User::class.java)
        }
    }

    fun registerData(vararg data: Data) {
        for (datum in data) {
            if (!datum.isUserData() && !datum.isUUIDData()) {
                keyPerData[datum.javaClass.name] = datum.getFullKey(null)
                continue
            }
            keyPerData[datum.javaClass.name] = datum.getFullKey("uuid")
        }
    }
}