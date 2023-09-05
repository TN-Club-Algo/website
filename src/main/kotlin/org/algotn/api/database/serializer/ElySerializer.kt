package org.algotn.api.database.serializer

import com.fasterxml.jackson.databind.module.SimpleModule
import java.util.function.Function

class ElySerializer : Serializer {

    companion object {
        val MODULES: MutableList<SimpleModule> = ArrayList()
    }

    override fun <T> register(
        clazz: Class<T>,
        serializer: Function<T, Map<*, *>>,
        deserializer: Function<Map<*, *>, T>
    ) {
        val simpleModule = SimpleModule(clazz.name)
        simpleModule.addSerializer(clazz, GenericSerializer(clazz, serializer))
        simpleModule.addDeserializer(clazz, GenericDeserializer(clazz, deserializer))
        MODULES.add(simpleModule)
    }
}