package com.test.kakaobank.domain.kakao.exception

data class KakaoExceptionResponse (
    val errorType: String,
    val message: String,
)