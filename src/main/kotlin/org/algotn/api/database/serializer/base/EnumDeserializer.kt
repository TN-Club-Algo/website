package org.algotn.api.database.serializer.base

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class EnumDeserializer<T : Enum<T>> : JsonDeserializer<Enum<T>>() {

    override fun deserialize(jsonParser: JsonParser?, ctxt: DeserializationContext?): Enum<T> {
        var clazz: Class<*>? = null
        var name: String? = null

        if (jsonParser == null) throw RuntimeException("jsonParser is null")

        if (jsonParser.currentToken == JsonToken.START_OBJECT) {
            jsonParser.nextToken()
        }

        var token = jsonParser.currentToken
        while (token != JsonToken.END_OBJECT) {
            val fieldName = jsonParser.currentName
            jsonParser.nextToken()

            if (fieldName.equals("type")) {
                clazz = javaClass.classLoader.loadClass(jsonParser.valueAsString)
            } else if (fieldName.equals("name")) {
                name = jsonParser.valueAsString
            }

            token = jsonParser.nextToken()
        }

        if (clazz == null) throw RuntimeException("clazz is null, not found in json")
        if (name == null) throw RuntimeException("name is null, not found in json")

        return java.lang.Enum.valueOf(clazz as Class<T>, name)
    }
}