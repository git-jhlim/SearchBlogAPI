package com.test.searchAPI.domain.naver.model

import java.time.LocalDateTime

data class NaverSearchResponse<T> (
    val lastBuildDate: LocalDateTime,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<T>
)