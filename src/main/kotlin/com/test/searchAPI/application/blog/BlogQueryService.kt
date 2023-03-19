package com.test.searchAPI.application.blog

import com.test.searchAPI.application.blog.model.BlogSearchModel
import com.test.searchAPI.application.blog.model.BlogSearchResponse
import com.test.searchAPI.common.model.PageResponse
import com.test.searchAPI.domain.kakao.KakaoSearchDomainService
import com.test.searchAPI.domain.keyword.event.PopularKeywordEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class BlogQueryService(
    private val kakaoSearchDomainService: KakaoSearchDomainService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    suspend fun searchBlog(searchModel: BlogSearchModel): PageResponse<BlogSearchResponse> {
        val result = kakaoSearchDomainService.searchBlog(searchModel.toBlogSearchParams())
            .also { applicationEventPublisher.publishEvent(PopularKeywordEvent(searchModel.keyword)) }

        return PageResponse.convert(result) { BlogSearchResponse.of(it) }
    }
}