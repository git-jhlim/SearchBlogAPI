package com.test.searchAPI.application.blog

import com.test.searchAPI.application.blog.model.BlogSearchModel
import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.common.exception.BadRequestException
import com.test.searchAPI.domain.kakao.KakaoSearchDomainService
import com.test.searchAPI.domain.naver.NaverSearchDomainService
import com.test.searchAPI.kakaoSearchResult
import com.test.searchAPI.naverSearchResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.reactive.function.client.WebClientResponseException
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@ExtendWith(MockKExtension::class)
class BlogQueryServiceTest {
    private lateinit var blogQueryService: BlogQueryService

    @MockK
    lateinit var kakaoSearchDomainService: KakaoSearchDomainService

    @MockK
    lateinit var naverSearchDomainService: NaverSearchDomainService

    @MockK
    lateinit var applicationEventPublisher: ApplicationEventPublisher

    private val searchModel = BlogSearchModel(
        keyword = "날씨",
        sorting = BlogSearchSort.ACCURACY,
        page = 1,
        size = 40,
    )

    @BeforeEach
    fun setup() {
        blogQueryService = BlogQueryService(kakaoSearchDomainService, applicationEventPublisher, naverSearchDomainService)
    }

    @Test
    fun `카카오API 조회한 결과 값과 동일한 return 값을 갖는다`() {
        val searchPrams = searchModel.toKakaoBlogSearchParams()

        coEvery { kakaoSearchDomainService.searchBlog(searchPrams) } returns kakaoSearchResult
        every { applicationEventPublisher.publishEvent(any() as JvmType.Object) } just runs

        val result = runBlocking {
            blogQueryService.searchBlog(searchModel)
        }

        assert(result.contents.size == kakaoSearchResult.contents.size)
        assert(result.totalCount == kakaoSearchResult.totalCount)

        coVerify { kakaoSearchDomainService.searchBlog(any()) }
        coVerify(inverse = true) { naverSearchDomainService.searchBlog(any()) }
    }

    @Test
    fun `블로그 검색 시, 검색어 조회 카운트 증가 이벤트는 한 번 발행`() {
        val searchPrams = searchModel.toKakaoBlogSearchParams()

        coEvery { kakaoSearchDomainService.searchBlog(searchPrams) } returns kakaoSearchResult
        every { applicationEventPublisher.publishEvent(any() as JvmType.Object) } just runs

        runBlocking {
            blogQueryService.searchBlog(searchModel)
        }

        coVerify(exactly = 1) { applicationEventPublisher.publishEvent(any() as JvmType.Object) }
    }

    @Test
    fun `다음 블로그 검색 실패(WebClientResponseException) 시, 네이버 블로그 검색`() {
        val searchPrams = searchModel.toKakaoBlogSearchParams()
        val naverSearchPrams = searchModel.toNaverBlogSearchParams()

        every { applicationEventPublisher.publishEvent(any() as JvmType.Object) } just runs
        coEvery { kakaoSearchDomainService.searchBlog(searchPrams) } throws mockk<WebClientResponseException>()
        coEvery { naverSearchDomainService.searchBlog(naverSearchPrams) } returns naverSearchResult

        runBlocking {
            blogQueryService.searchBlog(searchModel)
        }

        coVerify(exactly = 1) { naverSearchDomainService.searchBlog(naverSearchPrams) }
    }

    @Test
    fun `다음 블로그 검색 실패(CommonException) 시, 그대로 throw`() {
        val searchPrams = searchModel.toKakaoBlogSearchParams()

        every { applicationEventPublisher.publishEvent(any() as JvmType.Object) } just runs
        coEvery { kakaoSearchDomainService.searchBlog(searchPrams) } throws BadRequestException()

        assertThrows<BadRequestException> {
            runBlocking {
                blogQueryService.searchBlog(searchModel)
            }
        }

        coVerify(inverse = true) { naverSearchDomainService.searchBlog(any()) }
    }

    @Test
    fun `다음 블로그 검색 실패, 네이버 블로그 검색 실패 시 exception 발생`() {
        val searchPrams = searchModel.toKakaoBlogSearchParams()
        val naverSearchPrams = searchModel.toNaverBlogSearchParams()

        every { applicationEventPublisher.publishEvent(any() as JvmType.Object) } just runs
        coEvery { kakaoSearchDomainService.searchBlog(searchPrams) } throws mockk<WebClientResponseException>()
        coEvery { naverSearchDomainService.searchBlog(naverSearchPrams) } throws mockk<WebClientResponseException>()

        assertThrows<WebClientResponseException> {
            runBlocking {
                blogQueryService.searchBlog(searchModel)
            }
        }

        coVerifyAll {
            kakaoSearchDomainService.searchBlog(searchPrams)
            naverSearchDomainService.searchBlog(naverSearchPrams)
        }
    }
}