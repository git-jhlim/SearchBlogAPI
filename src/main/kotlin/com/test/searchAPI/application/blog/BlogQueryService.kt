package com.test.searchAPI.application.blog

import com.test.searchAPI.application.blog.model.BlogSearchModel
import com.test.searchAPI.application.blog.model.BlogSearchResponse
import com.test.searchAPI.common.exception.CommonException
import com.test.searchAPI.common.model.BlogPageResponse
import com.test.searchAPI.domain.kakao.KakaoSearchDomainService
import com.test.searchAPI.domain.keyword.event.SearchKeywordEvent
import com.test.searchAPI.domain.naver.NaverSearchDomainService
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class BlogQueryService(
    private val kakaoSearchDomainService: KakaoSearchDomainService,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val naverSearchDomainService: NaverSearchDomainService,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    suspend fun searchBlog(searchModel: BlogSearchModel): BlogPageResponse<BlogSearchResponse> {
        applicationEventPublisher.publishEvent(SearchKeywordEvent(searchModel.keyword))

        return  runCatching {
            kakaoSearchDomainService.searchBlog(searchModel.toKakaoBlogSearchParams())
                .let { result -> BlogPageResponse.convert(result) { BlogSearchResponse.of(it) } }
        }.getOrElse {ex ->
            when(ex) {
                is CommonException -> throw ex
                else -> {
                    logger.info("[kakaoSearchDomainService.searchBlog] Exception throw", ex)
                    naverSearchDomainService.searchBlog(searchModel.toNaverBlogSearchParams())
                        .let { result -> BlogPageResponse.convert(result) { BlogSearchResponse.of(it) } }
                }
            }
        }
    }
}
