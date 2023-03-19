package com.test.searchAPI.domain.kakao.exception

data class KakaoExceptionResponse (
    val errorType: String,
    val message: String,
)