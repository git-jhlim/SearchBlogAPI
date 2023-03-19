package com.test.kakaobank.common.exception

open class CommonException(
    val errorCode: ErrorCode,
    val arg: String,
    open val detail: Map<String, Any> = emptyMap(),
    override val message: String = errorCode.getMessage(arg),
): RuntimeException(message)

class BadRequestException(
    detail: Map<String, Any> = emptyMap(),
): CommonException(
    errorCode = CommonErrorCode.BAD_REQUEST,
    arg = "",
    detail = detail,
)