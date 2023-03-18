package com.test.kakaobank.presentation

import com.test.kakaobank.application.SearchQueryService
import com.test.kakaobank.common.enum.BlogSearchSort
import com.test.kakaobank.common.extension.queryParamToIntOrNull
import com.test.kakaobank.presentation.error.InvalidArgumentException
import com.test.kakaobank.presentation.model.BlogSearchRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class SearchHandler(
    private val searchQueryService: SearchQueryService,
) {
    suspend fun searchBlog(request: ServerRequest): ServerResponse {
        val keyword = request.queryParamOrNull("keyword")
        val sorting = request.queryParamOrNull("sorting")
            ?.let {
                BlogSearchSort.getBy(it) ?: throw InvalidArgumentException("sorting")
            }
        val page = request.queryParamToIntOrNull("page")
        val size = request.queryParamToIntOrNull("size")


        val param = BlogSearchRequest.of(keyword, sorting, page, size)
        return ServerResponse.ok().bodyValueAndAwait(
            searchQueryService.searchBlog(param.toBlogSearchModel())
        )
    }

}
