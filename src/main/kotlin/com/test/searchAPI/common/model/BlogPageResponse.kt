package com.test.searchAPI.common.model

import com.test.searchAPI.common.enum.SearchBaseType


data class BlogPageResponse<T>(
    override val totalCount: Int,
    override val page: Int,
    override val contents: List<T>,
    val baseOn: SearchBaseType,
): PageResponse<T> {

    companion object {
        fun <T, V> convert(page: BlogPageResponse<T>, transform: (T) -> V): BlogPageResponse<V> {
            return BlogPageResponse(
                totalCount = page.totalCount,
                page = page.page,
                contents = page.contents.map { transform(it) },
                baseOn = page.baseOn,
            )
        }
    }
}

interface PageResponse<T> {
    val totalCount: Int
    val page: Int
    val contents: List<T>
}