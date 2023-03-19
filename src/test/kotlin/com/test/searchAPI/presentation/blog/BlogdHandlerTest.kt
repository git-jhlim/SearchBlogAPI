package com.test.searchAPI.presentation.blog

import com.test.searchAPI.application.blog.BlogQueryService
import com.test.searchAPI.presentation.blog.exception.BlogErrorCode
import com.test.searchAPI.presentation.blog.exception.InvalidPageException
import com.test.searchAPI.presentation.blog.exception.InvalidSizeException
import com.test.searchAPI.presentation.exception.APIErrorCode
import com.test.searchAPI.presentation.exception.InvalidParameterException
import io.mockk.coEvery
import io.mockk.coVerify
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

@ExtendWith(MockKExtension::class)
class BlogdHandlerTest {

    @MockK
    lateinit var blogQueryService: BlogQueryService
    private lateinit var blogHandler: BlogHandler

    @BeforeEach
    fun setup() {
        blogHandler = BlogHandler(blogQueryService)
    }

    @Test
    fun `블로그 검색 시 keyword 파라미터는 필수`() {
        val request = MockServerRequest.builder().build()

        val exception = assertThrows<InvalidParameterException> {
            runBlocking {
                blogHandler.searchBlog(request)
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
                blogHandler.searchBlog(request)
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

        coEvery { blogQueryService.searchBlog(any()) } returns mockk()

        val response = runBlocking {
            blogHandler.searchBlog(request)
        }

        assert(response.statusCode() == HttpStatus.OK)
        coVerify { blogQueryService.searchBlog(any()) }
    }

    @Test
    fun `블로그 검색 시 page 는 양수`() {
        val request = MockServerRequest.builder()
            .queryParam("keyword", "검색")
            .queryParam("sorting", "ACCURACY")
            .queryParam("page", "0")
            .build()

        val exception = assertThrows<InvalidPageException> {
            runBlocking {
                blogHandler.searchBlog(request)
            }
        }

        assert(exception.errorCode == BlogErrorCode.PAGE_HAS_INVALID_NUMBER)
    }

    @Test
    fun `블로그 검색 시 size 는 양수`() {
        val request = MockServerRequest.builder()
            .queryParam("keyword", "검색")
            .queryParam("sorting", "ACCURACY")
            .queryParam("size", "-1")
            .build()

        val exception = assertThrows<InvalidSizeException> {
            runBlocking {
                blogHandler.searchBlog(request)
            }
        }

        assert(exception.errorCode == BlogErrorCode.SIZE_HAS_INVALID_NUMBER)
    }
}