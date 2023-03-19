package com.test.kakaobank.presentation

import com.test.kakaobank.application.SearchQueryService
import com.test.kakaobank.presentation.exception.APIErrorCode
import com.test.kakaobank.presentation.exception.InvalidParameterException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import org.springframework.web.reactive.function.server.ServerRequest

@ExtendWith(MockKExtension::class)
class SearchHandlerTest {

    @MockK
    lateinit var searchQueryService: SearchQueryService
    private lateinit var searchHandler: SearchHandler

    @BeforeEach
    fun setup() {
        searchHandler = SearchHandler(searchQueryService)
    }

    @Test
    fun `블로그 검색 시 keyword 파라미터는 필수`() {
        val request = MockServerRequest.builder().build()

        val exception = assertThrows<InvalidParameterException> {
            runBlocking {
                searchHandler.searchBlog(request)
            }
        }

        assert(exception.errorCode == APIErrorCode.INVALID_PARAMETER)
        assert(exception.arg == "keyword")
    }

    @Test
    fun `블로그 검색 시 sorting 유효하지 않은 값의 경우 exception`() {
        val request = MockServerRequest.builder()
            .queryParam("keyword", "검색")
            .queryParam("sorting", "invalid")
            .build()

        val exception = assertThrows<InvalidParameterException> {
            runBlocking {
                searchHandler.searchBlog(request)
            }
        }

        assert(exception.errorCode == APIErrorCode.INVALID_PARAMETER)
        assert(exception.arg == "sorting")
    }

    @Test
    fun `블로그 검색 시 sorting, keyword 유효한 값이면 정상 동작`() {
        val request = MockServerRequest.builder()
            .queryParam("keyword", "검색")
            .queryParam("sorting", "ACCURACY")
            .build()

        coEvery { searchQueryService.searchBlog(any()) } returns mockk()

        val response = runBlocking {
            searchHandler.searchBlog(request)
        }

        assert(response.statusCode() == HttpStatus.OK)
        coVerify { searchQueryService.searchBlog(any()) }
    }

    @Test
    fun `블로그 검색 시 page 는 양수`() {
        val request = MockServerRequest.builder()
            .queryParam("keyword", "검색")
            .queryParam("sorting", "ACCURACY")
            .queryParam("page", "0")
            .build()

        val exception = assertThrows<InvalidParameterException> {
            runBlocking {
                searchHandler.searchBlog(request)
            }
        }

        assert(exception.errorCode == APIErrorCode.INVALID_PARAMETER)
        assert(exception.arg == "page")
    }

    @Test
    fun `블로그 검색 시 size 는 양수`() {
        val request = MockServerRequest.builder()
            .queryParam("keyword", "검색")
            .queryParam("sorting", "ACCURACY")
            .queryParam("size", "-1")
            .build()

        val exception = assertThrows<InvalidParameterException> {
            runBlocking {
                searchHandler.searchBlog(request)
            }
        }

        assert(exception.errorCode == APIErrorCode.INVALID_PARAMETER)
        assert(exception.arg == "size")
    }

    @Test
    fun `인기 검색어 조회`() {
        val request = mockk<ServerRequest>()

        coEvery { searchQueryService.getPopularKeywords() } returns mockk()

        val response = runBlocking {
            searchHandler.getPopularKeywords(request)
        }

        assert(response.statusCode() == HttpStatus.OK)
        coVerify { searchQueryService.getPopularKeywords() }
    }
}