package com.test.searchAPI.common.exception

import com.test.searchAPI.common.PropertiesMessage

enum class CommonErrorCode(
    override val code: String,
    override val messageKey: String,
): ErrorCode {
    INTERNAL_ERROR("CECD0001", "commonErrorCode.internalServerError"),
    BAD_REQUEST("CECD0002", "commonErrorCode.badRequest"),
    ;

    override fun getMessage(arg: String?): String {
        return PropertiesMessage.getMessage(messageKey, arrayOf(arg))
    }
}