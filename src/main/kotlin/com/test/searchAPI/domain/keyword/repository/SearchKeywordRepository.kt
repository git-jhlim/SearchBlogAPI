package com.test.searchAPI.domain.keyword.repository

import com.test.searchAPI.domain.keyword.entity.SearchKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface SearchKeywordRepository: JpaRepository<SearchKeyword, Int>, SearchKeywordRepositoryCustom {
    fun findByKeyword(keyword: String): SearchKeyword?
}