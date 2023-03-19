package com.test.kakaobank.presentation.exception

import com.test.kakaobank.common.exception.CommonException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidParameterException(field: String) : CommonException(PresentationErrorCode.INVALID_PARAMETER, field) {
}