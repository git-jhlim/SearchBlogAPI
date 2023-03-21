package com.test.searchAPI.presentation.blog

import com.test.searchAPI.application.blog.BlogQueryService
import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.common.extension.queryParamOrThrow
import com.test.searchAPI.common.extension.queryParamToIntOrNull
import com.test.searchAPI.presentation.exception.InvalidParameterException
import com.test.searchAPI.presentation.blog.model.BlogSearchRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class BlogHandler(
    private val blogQueryService: BlogQueryService,
) {
    suspend fun searchBlog(request: ServerRequest): ServerResponse {
        val keyword = request.queryParamOrThrow("keyword")
        val sorting = request.queryParamOrNull("sorting")
            ?.let {
                BlogSearchSort.getBy(it) ?: throw InvalidParameterException("sorting")
            }
        val page = request.queryParamToIntOrNull("page")
        val size = request.queryParamToIntOrNull("size")

        val param = BlogSearchRequest.of(keyword, sorting, page, size)
            .also { it.valid() }
        return ServerResponse.ok().bodyValueAndAwait(
            blogQueryService.searchBlog(param.toBlogSearchModel())
        )
    }
}
