package com.test.searchAPI.presentation.exception

import com.test.searchAPI.common.exception.CommonException

class InvalidParameterException(arg: String) : CommonException(APIErrorCode.INVALID_PARAMETER, arg)