package com.test.kakaobank.domain.popularkeyword

import com.test.kakaobank.domain.popularkeyword.entity.PopularKeyword
import com.test.kakaobank.domain.popularkeyword.repository.PopularKeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PopularKeywordService(
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