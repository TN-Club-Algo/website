package org.algotn.api.database.serializer.base

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException

class EnumSerializer : JsonSerializer<Enum<*>>() {

    @Throws(IOException::class)
    override fun serialize(value: Enum<*>, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeStringField("@class", Enum::class.java.name)
        gen.writeStringField("type", value.javaClass.name)
        gen.writeStringField("name", value.name)
        gen.writeEndObject()
    }
}