package com.test.kakaobank.presentation.exception

import com.test.kakaobank.common.PropertiesMessage
import com.test.kakaobank.common.exception.ErrorCode

enum class PresentationErrorCode(
    override val code: String,
    override val messageKey: String,
): ErrorCode {
    INVALID_PARAMETER("PE0001", "presentationErrorCode.invalidParameter"),
    ;

    override fun getMessage(arg: String): String {
        return PropertiesMessage.getMessage(messageKey, arrayOf(arg))
    }
}