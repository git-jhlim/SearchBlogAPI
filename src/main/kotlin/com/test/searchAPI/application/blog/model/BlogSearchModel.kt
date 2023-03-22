package com.test.searchAPI.application.blog.model

import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.domain.kakao.model.KakaoBlogSearchParams
import com.test.searchAPI.domain.naver.model.NaverBlogSearchParams

data class BlogSearchModel (
    val keyword: String,
    val sorting: BlogSearchSort,
    val page: Int,
    val size: Int,
) {
    fun toKakaoBlogSearchParams() = KakaoBlogSearchParams(
        query = keyword,
        sort = sorting.kakao.value,
        page = page,
        size = size,
    )

    fun toNaverBlogSearchParams() = NaverBlogSearchParams(
        query = keyword,
        sort = sorting.naver.value,
        start = getOffset(),
        display = size,
    )

    private fun getOffset() = 1 + (page - 1) * size
}