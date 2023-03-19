package com.test.kakaobank.application.model

import com.test.kakaobank.domain.popularkeyword.entity.PopularKeyword

data class PopularKeywordResponse (
    val keyword: String,
    val searchCount: Int,
) {
    companion object {
        fun of(keyword: PopularKeyword) = PopularKeywordResponse(keyword.keyword, keyword.searchCount)
    }
}