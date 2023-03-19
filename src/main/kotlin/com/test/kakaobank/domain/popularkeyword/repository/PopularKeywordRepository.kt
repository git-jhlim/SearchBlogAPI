package com.test.kakaobank.domain.popularkeyword.repository

import com.test.kakaobank.domain.popularkeyword.entity.PopularKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface PopularKeywordRepository: JpaRepository<PopularKeyword, Long> {
    fun findByKeyword(keyword: String): PopularKeyword?
    fun findTop10ByOrderBySearchCountDesc(): List<PopularKeyword>
}