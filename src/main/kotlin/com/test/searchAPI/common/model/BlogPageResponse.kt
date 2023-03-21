package com.test.searchAPI.common.model

import com.test.searchAPI.common.enum.SearchBaseType


data class BlogPageResponse<T>(
    override val totalCount: Int,
    override val page: Int,
    override val pageSize: Int,
    override val totalPages: Int,
    override val hasPrevious: Boolean,
    override val hasNext: Boolean,
    override val contents: List<T>,
    val baseOn: SearchBaseType,
): PageResponse<T> {

    companion object {
        fun <T, V> convert(page: BlogPageResponse<T>, transform: (T) -> V): BlogPageResponse<V> {
            return BlogPageResponse(
                totalCount = page.totalCount,
                page = page.page,
                pageSize = page.pageSize,
                contents = page.contents.map { transform(it) },
                baseOn = page.baseOn,
                totalPages = page.totalPages,
                hasNext = page.hasNext,
                hasPrevious = page.hasPrevious,
            )
        }
    }
}

interface PageResponse<T> {
    val totalCount: Int
    val page: Int
    val pageSize: Int
    val totalPages: Int
    val hasPrevious: Boolean
    val hasNext: Boolean
    val contents: List<T>
}