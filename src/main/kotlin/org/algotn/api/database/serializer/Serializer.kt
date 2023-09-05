package org.algotn.api.database.serializer

import java.util.function.Function

interface Serializer {

    fun <T> register(clazz: Class<T>, serializer: Function<T, Map<*, *>>, deserializer: Function<Map<*, *>, T>)
}