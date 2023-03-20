package com.test.searchAPI.domain.keyword

import com.test.searchAPI.domain.keyword.model.PopularKeyword
import com.test.searchAPI.domain.keyword.repository.SearchKeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KeywordDomainService(
    private val searchKeywordRepository: SearchKeywordRepository,
) {
    @Transactional(readOnly = true)
    fun getPopularKeyword(): List<PopularKeyword> {
        return searchKeywordRepository.getPopularKeyword()
    }
}