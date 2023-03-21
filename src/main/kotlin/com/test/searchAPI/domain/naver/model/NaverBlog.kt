package com.test.searchAPI.domain.naver.model

import java.time.LocalDate

data class NaverBlog (
    val title: String,
    val link: String,
    val description: String,
    val bloggername: String,
    val bloggerlink: String,
    val postdate: LocalDate,
)