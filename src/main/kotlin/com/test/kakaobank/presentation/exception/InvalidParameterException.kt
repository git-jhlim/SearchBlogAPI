package com.test.kakaobank.presentation.exception

import com.test.kakaobank.common.exception.CommonException
import org.springframework.web.bind.annotation.ResponseStatus

class InvalidParameterException(arg: String) : CommonException(APIErrorCode.INVALID_PARAMETER, arg)