package com.test.searchAPI.presentation.blog.exception

import com.test.searchAPI.common.exception.CommonException

class InvalidPageException(): CommonException(BlogErrorCode.PAGE_HAS_INVALID_NUMBER)
class InvalidSizeException(): CommonException(BlogErrorCode.SIZE_HAS_INVALID_NUMBER)
class InvalidKeywordLengthException(): CommonException(BlogErrorCode.INVALID_KEYWORD_LENGTH)