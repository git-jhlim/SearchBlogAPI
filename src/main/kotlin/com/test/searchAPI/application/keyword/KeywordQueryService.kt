package com.test.searchAPI.application.keyword

import com.test.searchAPI.application.keyword.model.PopularKeywordResponse
import com.test.searchAPI.domain.keyword.KeywordDomainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class KeywordQueryService(
    private val keywordDomainService: KeywordDomainService,
) {
    suspend fun getPopularKeywords(): List<PopularKeywordResponse> {
        return withContext(Dispatchers.IO) {
            keywordDomainService.getPopularKeyword()
        }.mapIndexed { idx, keyword -> PopularKeywordResponse.of(idx, keyword ) }
    }
}