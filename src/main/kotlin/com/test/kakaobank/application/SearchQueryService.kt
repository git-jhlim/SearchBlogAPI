package com.test.kakaobank.application

import com.test.kakaobank.application.model.BlogSearchModel
import com.test.kakaobank.application.model.BlogSearchResponse
import com.test.kakaobank.application.model.PopularKeywordResponse
import com.test.kakaobank.common.model.PageResponse
import com.test.kakaobank.domain.kakao.KakaoSearchDomainService
import com.test.kakaobank.domain.popularkeyword.PopularKeywordService
import com.test.kakaobank.domain.popularkeyword.event.PopularKeywordEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SearchQueryService(
    private val kakaoSearchDomainService: KakaoSearchDomainService,
    private val popularKeywordService: PopularKeywordService,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    suspend fun searchBlog(searchModel: BlogSearchModel): PageResponse<BlogSearchResponse> {
        val result = kakaoSearchDomainService.searchBlog(searchModel.toBlogSearchParams())
            .also { applicationEventPublisher.publishEvent(PopularKeywordEvent(searchModel.keyword)) }

        return PageResponse.convert(result) { BlogSearchResponse.of(it) }
    }

    suspend fun getPopularKeywords(): List<PopularKeywordResponse> {
        return popularKeywordService.getPopularKeyword()
            .map { PopularKeywordResponse.of(it) }
    }
}