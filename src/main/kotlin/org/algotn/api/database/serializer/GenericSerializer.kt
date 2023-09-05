package org.algotn.api.database.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import java.io.IOException
import java.util.function.Function

class GenericSerializer<T>(private val clazz: Class<T>, private val serializer: Function<T, Map<*, *>>) :
    JsonSerializer<T>() {

    private fun serialize(t: T): Map<Any, Any> {
        return serializer.apply(t) as Map<Any, Any>
    }

    @Throws(IOException::class)
    override fun serializeWithType(
        value: T,
        gen: JsonGenerator,
        serializers: SerializerProvider,
        typeSer: TypeSerializer
    ) {
        serialize(value, gen, serializers)
    }

    @Throws(IOException::class)
    override fun serialize(t: T, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
        val map = serialize(t)
        jsonGenerator.writeStartObject()
        jsonGenerator.writeObjectField("@class", clazz.name)
        for ((key, value) in map) {
            jsonGenerator.writeObjectField(key.toString(), value)
        }
        jsonGenerator.writeEndObject()
    }

    override fun handledType(): Class<T> {
        return clazz
    }
}