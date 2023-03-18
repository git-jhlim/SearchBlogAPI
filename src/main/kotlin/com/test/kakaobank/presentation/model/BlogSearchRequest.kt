package com.test.kakaobank.presentation.model

import com.test.kakaobank.application.model.BlogSearchModel
import com.test.kakaobank.common.enum.BlogSearchSort

data class BlogSearchRequest(
    val keyword: String,
    val sorting: BlogSearchSort,
    val page: Int,
    val size: Int,
) {
    fun toBlogSearchModel() = BlogSearchModel(keyword, sorting, page, size)
    companion object {
        fun of(keyword: String?, sorting: BlogSearchSort?, page: Int?, size: Int?): BlogSearchRequest {
            return BlogSearchRequest(
                keyword = keyword ?: "",
                sorting = sorting ?: BlogSearchSort.ACCURACY,
                page = page ?: 1,
                size = size ?: 10,
            )
        }
    }
}