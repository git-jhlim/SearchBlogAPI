package com.test.searchAPI.domain.keyword

import com.test.searchAPI.domain.keyword.entity.PopularKeyword
import com.test.searchAPI.domain.keyword.repository.PopularKeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class KeywordDomainService(
    private val popularKeywordRepository: PopularKeywordRepository,
) {
    fun addSearchCount(keyword: String) {
        popularKeywordRepository.findByKeyword(keyword)
            ?.also { it.addSearchCount() }
            ?: popularKeywordRepository.save(PopularKeyword(keyword))
    }

    fun getPopularKeyword(): List<PopularKeyword> {
        return popularKeywordRepository.findTop10ByOrderBySearchCountDesc()
    }
}