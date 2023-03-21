package com.test.searchAPI.presentation.blog.model

import com.test.searchAPI.application.blog.model.BlogSearchModel
import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.presentation.blog.exception.*

data class BlogSearchRequest(
    val keyword: String,
    val sorting: BlogSearchSort,
    val page: Int,
    val size: Int,
) {
    fun toBlogSearchModel() = BlogSearchModel(keyword, sorting, page, size)

    fun valid() {
        if(page !in MIN_LENGTH..MAX_PAGE_AND_SIZE_LENGTH)
            throw InvalidPageException()
        if(size !in MIN_LENGTH..MAX_PAGE_AND_SIZE_LENGTH)
            throw InvalidSizeException()
        if(keyword.length !in MIN_LENGTH..MAX_KEYWORD_LENGTH)
            throw InvalidKeywordLengthException()
    }

    companion object {
        private const val MAX_PAGE_AND_SIZE_LENGTH = 50
        private const val MIN_LENGTH = 1
        private const val MAX_KEYWORD_LENGTH = 250
        fun of(keyword: String, sorting: BlogSearchSort?, page: Int?, size: Int?): BlogSearchRequest {
            return BlogSearchRequest(
                keyword = keyword,
                sorting = sorting ?: BlogSearchSort.ACCURACY,
                page = page ?: 1,
                size = size ?: 10,
            )
        }
    }
}