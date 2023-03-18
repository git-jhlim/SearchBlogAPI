package com.test.kakaobank.application

import com.test.kakaobank.application.model.BlogSearchModel
import com.test.kakaobank.application.model.BlogSearchResponse
import com.test.kakaobank.common.model.PageResponse
import com.test.kakaobank.domain.kakao.KakaoSearchDomainService
import org.springframework.stereotype.Service

@Service
class SearchQueryService(
    private val kakaoSearchDomainService: KakaoSearchDomainService
) {
    suspend fun searchBlog(searchModel: BlogSearchModel): PageResponse<BlogSearchResponse> {
        val result = kakaoSearchDomainService.searchBlog(searchModel.toBlogSearchParams())
            .let { response ->
                PageResponse.convert(response) { BlogSearchResponse.of(it) }
            }
        return result
    }
}