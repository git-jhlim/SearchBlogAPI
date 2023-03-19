package com.test.kakaobank.common.extension

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val objectMapper: ObjectMapper = ObjectMapper()
    .registerModule(KotlinModule.Builder().build())
    .registerModule(
        JavaTimeModule().apply {
            addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        }
    )
inline fun <reified T> String.toModel(): T {
    return objectMapper.readValue(this, jacksonTypeRef<T>()
    )
}