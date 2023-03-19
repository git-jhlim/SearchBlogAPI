package com.test.searchAPI.presentation.blog.exception

import com.test.searchAPI.common.PropertiesMessage
import com.test.searchAPI.common.exception.ErrorCode

enum class BlogErrorCode(
    override val code: String,
    override val messageKey: String,
): ErrorCode {
    PAGE_HAS_INVALID_NUMBER("BECD0001", "blogErrorCode.pageHasInvalidNumber"),
    SIZE_HAS_INVALID_NUMBER("BECD0002", "blogErrorCode.sizeHasInvalidNumber"),
    ;

    override fun getMessage(arg: String?): String {
        return PropertiesMessage.getMessage(messageKey, arrayOf(arg))
    }
}