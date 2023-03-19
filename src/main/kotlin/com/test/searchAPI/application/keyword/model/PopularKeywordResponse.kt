package com.test.searchAPI.application.keyword.model

import com.test.searchAPI.domain.keyword.entity.PopularKeyword

data class PopularKeywordResponse (
    val index: Int,
    val keyword: String,
    val searchCount: Int,
) {
    companion object {
        fun of(index: Int, keyword: PopularKeyword) = PopularKeywordResponse(index, keyword.keyword, keyword.searchCount)
    }
}