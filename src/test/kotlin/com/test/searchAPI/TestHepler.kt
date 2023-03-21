package com.test.searchAPI

import com.test.searchAPI.common.enum.SearchBaseType
import com.test.searchAPI.common.model.BlogPageResponse
import com.test.searchAPI.domain.kakao.model.KakaoBlog
import com.test.searchAPI.domain.naver.model.NaverBlog
import java.time.LocalDate
import java.time.LocalDateTime

val kakaoSearchResult = BlogPageResponse(
    baseOn = SearchBaseType.KAKAO,
    page = 1,
    totalCount = 1,
    hasNext = false,
    hasPrevious = false,
    pageSize = 10,
    totalPages = 1,
    contents = listOf(
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

val naverSearchResult = BlogPageResponse(
    baseOn = SearchBaseType.NAVER,
    page = 1,
    totalCount = 1,
    hasNext = false,
    hasPrevious = false,
    pageSize = 10,
    totalPages = 1,
    contents = listOf(
        NaverBlog(
            bloggername = "순자",
            bloggerlink = "naverblog.com/soonZZa/11123111",
            title = "블로그 챌린지",
            description = "1주차 다이어리 ...",
            postdate = LocalDate.now(),
            link = "naverblog.com/soonZZa"
        )
    )
)
