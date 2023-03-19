package com.test.kakaobank.common.exception

open class CommonException(
    val errorCode: ErrorCode,
    val arg: String,
    override val message: String = errorCode.getMessage(arg),
): RuntimeException(message)