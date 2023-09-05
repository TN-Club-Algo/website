package org.algotn.api.database.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.util.function.Function

class GenericDeserializer<T>(private val clazz: Class<T>, private val deserializer: Function<Map<*, *>, T>) :
    JsonDeserializer<T>() {

    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): T {
        val deserializedMap: MutableMap<String, Any> = HashMap()
        if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
            jsonParser.nextToken()
        }
        var token = jsonParser.currentToken()
        while (token != JsonToken.END_OBJECT) {
            val fieldName = jsonParser.currentName
            jsonParser.nextToken()
            if ("@class" != fieldName) {
                val fieldValue = jsonParser.readValueAs(Any::class.java)
                deserializedMap[fieldName] = fieldValue
            }
            token = jsonParser.nextToken()
        }
        return deserializer.apply(deserializedMap)
    }

    override fun handledType(): Class<*> {
        return clazz
    }
}