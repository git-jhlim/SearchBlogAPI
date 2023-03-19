package com.test.searchAPI.domain.kakao.model

import java.time.LocalDateTime

data class KakaoBlog (
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val thumbnail: String,
    val datetime: LocalDateTime,
)