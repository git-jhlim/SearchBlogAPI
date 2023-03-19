package com.test.searchAPI.domain.keyword.repository

import com.test.searchAPI.domain.keyword.entity.PopularKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface PopularKeywordRepository: JpaRepository<PopularKeyword, Long> {
    fun findByKeyword(keyword: String): PopularKeyword?
    fun findTop10ByOrderBySearchCountDesc(): List<PopularKeyword>
}