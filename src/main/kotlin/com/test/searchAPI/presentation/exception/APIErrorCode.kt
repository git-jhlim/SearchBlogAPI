package com.test.searchAPI.presentation.exception

import com.test.searchAPI.common.PropertiesMessage
import com.test.searchAPI.common.exception.ErrorCode

enum class APIErrorCode(
    override val code: String,
    override val messageKey: String,
): ErrorCode {
    INVALID_PARAMETER("PE0001", "presentationErrorCode.invalidParameter"),
    ;

    override fun getMessage(arg: String?): String {
        return PropertiesMessage.getMessage(messageKey, arrayOf(arg))
    }
}