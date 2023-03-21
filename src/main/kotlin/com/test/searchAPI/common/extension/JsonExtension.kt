package com.test.searchAPI.common.extension

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val objectMapper: ObjectMapper = ObjectMapper()
    .registerModule(KotlinModule.Builder().build())
    .registerModule(
        JavaTimeModule().apply {
            addDeserializer(LocalDateTime::class.java, MultiLocalDateTimeDeserializer())
            addDeserializer(LocalDate::class.java, LocalDateDeserializer(DateTimeFormatter.BASIC_ISO_DATE))
        }
    )

inline fun <reified T> String.toModel(): T {
    return objectMapper.readValue(this, jacksonTypeRef<T>()
    )
}