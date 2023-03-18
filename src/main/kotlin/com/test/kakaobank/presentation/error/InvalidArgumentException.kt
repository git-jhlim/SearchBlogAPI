package com.test.kakaobank.presentation.error

class InvalidArgumentException(value: String) : RuntimeException() {
    override val message: String = "$value 를 확인해주세요"
}