package com.test.searchAPI.application.blog.model

import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.domain.kakao.model.BlogSearchParams

data class BlogSearchModel (
    val keyword: String,
    val url: String,
    val sorting: BlogSearchSort,
    val page: Int,
    val size: Int,
) {
    fun toBlogSearchParams() = BlogSearchParams(
        query = if(url.isNotBlank()) "$url $keyword" else keyword,
        sort = sorting.value,
        page = page,
        size = size,
    )
}