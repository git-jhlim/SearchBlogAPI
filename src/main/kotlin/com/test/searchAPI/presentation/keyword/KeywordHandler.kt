package com.test.searchAPI.presentation.keyword

import com.test.searchAPI.application.keyword.KeywordQueryService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class KeywordHandler(private val keywordQueryService: KeywordQueryService){
    suspend fun getPopularKeywords(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait(
            keywordQueryService.getPopularKeywords()
        )
    }
}