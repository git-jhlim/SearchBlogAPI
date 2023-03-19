package com.test.searchAPI.presentation.blog.model

import com.test.searchAPI.application.blog.model.BlogSearchModel
import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.presentation.blog.exception.InvalidPageException
import com.test.searchAPI.presentation.blog.exception.InvalidSizeException

data class BlogSearchRequest(
    val keyword: String,
    val url: String,
    val sorting: BlogSearchSort,
    val page: Int,
    val size: Int,
) {
    fun toBlogSearchModel() = BlogSearchModel(keyword, url, sorting, page, size)

    fun valid() {
        if(page !in MIN_LENGTH..MAX_LENGTH)
            throw InvalidPageException()
        if(size !in MIN_LENGTH..MAX_LENGTH)
            throw InvalidSizeException()
    }
    companion object {
        private const val MAX_LENGTH = 50
        private const val MIN_LENGTH = 1
        fun of(keyword: String, url: String?, sorting: BlogSearchSort?, page: Int?, size: Int?): BlogSearchRequest {
            return BlogSearchRequest(
                keyword = keyword,
                url = url?: "",
                sorting = sorting ?: BlogSearchSort.ACCURACY,
                page = page ?: 1,
                size = size ?: 10,
            )
        }
    }
}