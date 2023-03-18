package com.test.kakaobank.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class WebClientConfig {

    private val objectMapper = ObjectMapper()
        .registerModule(KotlinModule.Builder().build())
        .registerModule(
            JavaTimeModule().apply {
                addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
            }
        )
//        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    //.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    @Bean
    fun getWebClient(): WebClient {
        return WebClient.builder()
            .codecs {
                it.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)
                it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
                it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
            }
            .build()
    }
}