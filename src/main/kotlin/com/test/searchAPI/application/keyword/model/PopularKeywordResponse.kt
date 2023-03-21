package com.test.searchAPI.application.keyword.model

import com.test.searchAPI.domain.keyword.model.PopularKeyword

data class PopularKeywordResponse (
    val index: Int,
    val keyword: String,
    val searchCount: Long,
) {
    companion object {
        fun of(index: Int, keyword: PopularKeyword)
        = PopularKeywordResponse(index+1, keyword.keyword, keyword.searchCount)
    }
}