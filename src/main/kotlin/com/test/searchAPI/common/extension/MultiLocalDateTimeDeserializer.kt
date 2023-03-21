package com.test.searchAPI.common.extension

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.text.ParseException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


class MultiLocalDateTimeDeserializer: LocalDateTimeDeserializer() {

    private val formatters = listOf(
        DateTimeFormatter.ISO_OFFSET_DATE_TIME,
        DateTimeFormatter.RFC_1123_DATE_TIME,
    )

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
        val node: JsonNode = p.codec.readTree(p)
        val date: String = node.textValue()
        for (formatter in formatters) {
            try {
                return LocalDateTime.parse(date, formatter)
            } catch (e: DateTimeParseException) { }
        }
        throw ParseException("Can not parse date: \"$date\". using formats: $formatters", 0)
    }
}