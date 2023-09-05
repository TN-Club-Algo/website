package org.algotn.api.database.data

interface Data {

    companion object {
        fun <T : Data> fromMap(map: Map<String, Any?>, clazz: Class<out Data>): T {
            try {
                val data = clazz.constructors.first().newInstance()
                if (map.isNotEmpty()) {
                    for (field in clazz.declaredFields) {
                        // FIXME
                        if (true || !field.isAnnotationPresent(Internal::class.java)) {
                            field.isAccessible = true
                            field.set(data, map[field.name])
                        }
                    }
                }
                (data as Data).postInit()
                return data as T
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun postInit() {

    }

    fun isUserData(): Boolean {
        return true
    }

    fun isUUIDData(): Boolean {
        return false
    }

    fun getFullKey(email: String?): String {
        if (isUserData() && email != null) {
            return "users:${email}:${javaClass.simpleName.lowercase()}"
        }
        return javaClass.simpleName.lowercase() + if (email != null) ":$email" else ""
    }

    fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        for (field in javaClass.declaredFields) {
            field.isAccessible = true
            if (!field.isAnnotationPresent(Internal::class.java)) {
                map[field.name] = field.get(this)
            }
        }
        return map
    }
}