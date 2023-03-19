package com.test.searchAPI.presentation.keyword

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class KeywordRouter(private val keywordHandler: KeywordHandler) {
    @Bean
    fun coKeywordRouter(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/keywords").nest {
                GET("/popular-keywords", keywordHandler::getPopularKeywords)
            }
        }
    }
}