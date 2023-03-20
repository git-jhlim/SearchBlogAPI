package com.test.searchAPI.domain.keyword.repository

import com.test.searchAPI.domain.keyword.model.PopularKeyword


interface SearchKeywordRepositoryCustom {
    fun getPopularKeyword(): List<PopularKeyword>
}