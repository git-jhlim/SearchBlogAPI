package com.test.searchAPI.application.keyword

import com.test.searchAPI.application.blog.model.BlogSearchModel
import com.test.searchAPI.common.enum.BlogSearchSort
import com.test.searchAPI.domain.kakao.KakaoSearchDomainService
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
import java.util.Random

@ExtendWith(MockKExtension::class)
class KeywordQueryServiceTest {
    lateinit var keywordQueryService: KeywordQueryService

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
        keywordQueryService = KeywordQueryService(keywordDomainService)
    }

    @Test
    fun `인기검색어 조회 시, 검색횟수 내림 차순`() {
        val searchedKeywords = listOf(
            PopularKeyword(Random().nextInt(), "1위 인기검색어").apply { searchCount = 10000 },
            PopularKeyword(Random().nextInt(), "2위 인기검색어").apply { searchCount = 500 },
            PopularKeyword(Random().nextInt(), "3위 인기검색어").apply { searchCount = 10 },
        )
        every { keywordDomainService.getPopularKeyword() } returns searchedKeywords

        val result = runBlocking {
            keywordQueryService.getPopularKeywords()
        }

        assert(result.size == searchedKeywords.size)
        assert(result.first().keyword == searchedKeywords.first().keyword)
        assert(result.last().keyword == searchedKeywords.last().keyword)
    }
}