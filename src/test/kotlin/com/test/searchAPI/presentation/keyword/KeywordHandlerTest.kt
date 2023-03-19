package com.test.searchAPI.presentation.keyword

import com.test.searchAPI.application.keyword.KeywordQueryService
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
import org.springframework.web.reactive.function.server.ServerRequest

@ExtendWith(MockKExtension::class)
class KeywordHandlerTest {

    @MockK
    lateinit var keywordQueryService: KeywordQueryService
    private lateinit var keywordHandler: KeywordHandler

    @BeforeEach
    fun setup() {
        keywordHandler = KeywordHandler(keywordQueryService)
    }

    @Test
    fun `인기 검색어 조회`() {
        val request = mockk<ServerRequest>()

        coEvery { keywordQueryService.getPopularKeywords() } returns mockk()

        val response = runBlocking {
            keywordHandler.getPopularKeywords(request)
        }

        assert(response.statusCode() == HttpStatus.OK)
        coVerify { keywordQueryService.getPopularKeywords() }
    }
}