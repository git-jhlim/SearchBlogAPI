package com.test.kakaobank.presentation

import com.test.kakaobank.application.SearchQueryService
import com.test.kakaobank.common.enum.BlogSearchSort
import com.test.kakaobank.common.extension.queryParamOrThrow
import com.test.kakaobank.common.extension.queryParamToPositiveIntOrNull
import com.test.kakaobank.presentation.exception.InvalidParameterException
import com.test.kakaobank.presentation.model.BlogSearchRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class SearchHandler(
    private val searchQueryService: SearchQueryService,
) {
    suspend fun searchBlog(request: ServerRequest): ServerResponse {
        val keyword = request.queryParamOrThrow("keyword")
        val url = request.queryParamOrNull("url")
        val sorting = request.queryParamOrNull("sorting")
            ?.let {
                BlogSearchSort.getBy(it) ?: throw InvalidParameterException("sorting")
            }
        val page = request.queryParamToPositiveIntOrNull("page")
        val size = request.queryParamToPositiveIntOrNull("size")

        val param = BlogSearchRequest.of(keyword, url, sorting, page, size)
        return ServerResponse.ok().bodyValueAndAwait(
            searchQueryService.searchBlog(param.toBlogSearchModel())
        )
    }

    suspend fun getPopularKeywords(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait(
            searchQueryService.getPopularKeywords()
        )
    }

}
