package com.test.searchAPI.application.blog

import com.test.searchAPI.application.blog.model.BlogSearchModel
import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.domain.kakao.KakaoSearchDomainService
import com.test.searchAPI.domain.kakao.model.KakaoBlog
import com.test.searchAPI.domain.kakao.model.KakaoSearchResponse
import com.test.searchAPI.domain.keyword.KeywordDomainService
import com.test.searchAPI.domain.keyword.entity.PopularKeyword
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import java.time.LocalDateTime
import java.util.Random
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@ExtendWith(MockKExtension::class)
class BlogQueryServiceTest {
    lateinit var blogQueryService: BlogQueryService

    @MockK
    lateinit var kakaoSearchDomainService: KakaoSearchDomainService

    @MockK
    lateinit var keywordDomainService: KeywordDomainService

    @MockK
    lateinit var applicationEventPublisher: ApplicationEventPublisher

    private val searchModel = BlogSearchModel(
        keyword = "날씨",
        url = "",
        sorting = BlogSearchSort.ACCURACY,
        page = 1,
        size = 40,
    )
    @BeforeEach
    fun setup() {
        blogQueryService = BlogQueryService(kakaoSearchDomainService, applicationEventPublisher)
    }

    @Test
    fun `카카오API 조회한 결과 값과 동일한 return 값을 갖는다`() {
        val searchPrams = searchModel.toBlogSearchParams()
        val domainResult = KakaoSearchResponse(
            meta = KakaoSearchResponse.PageMetaInfo(1, 1, true) ,
            documents = listOf(
                KakaoBlog(
                    title = "2월 13일 블챌",
                    contents = "오늘 날씨는..",
                    url = "tistory.com/aaaa",
                    blogname = "새마음 블로그",
                    thumbnail = "//image.com",
                    datetime = LocalDateTime.now()
                )
            )
        )

        coEvery { kakaoSearchDomainService.searchBlog(searchPrams) } returns domainResult
        every { applicationEventPublisher.publishEvent(any() as JvmType.Object) } just runs

        val result = runBlocking {
            blogQueryService.searchBlog(searchModel)
        }

        assert(result.contents.size == domainResult.documents.size)
        assert(result.isEndPage == domainResult.meta.is_end)
        assert(result.totalCount == domainResult.meta.total_count)
        coVerify { kakaoSearchDomainService.searchBlog(any()) }
    }

    @Test
    fun `블로그 검색 시, 검색어 조회 카운트 증가 이벤트는 한 번 발행`() {
        val searchPrams = searchModel.toBlogSearchParams()
        val domainResult = KakaoSearchResponse(
            meta = KakaoSearchResponse.PageMetaInfo(1, 1, true) ,
            documents = listOf(
                KakaoBlog(
                    title = "2월 13일 블챌",
                    contents = "오늘 날씨는..",
                    url = "tistory.com/aaaa",
                    blogname = "새마음 블로그",
                    thumbnail = "//image.com",
                    datetime = LocalDateTime.now()
                )
            )
        )

        coEvery { kakaoSearchDomainService.searchBlog(searchPrams) } returns domainResult
        every { applicationEventPublisher.publishEvent(any() as JvmType.Object) } just runs

        runBlocking {
            blogQueryService.searchBlog(searchModel)
        }

        coVerify(atMost = 1, atLeast = 1) { applicationEventPublisher.publishEvent(any() as JvmType.Object) }
    }
}