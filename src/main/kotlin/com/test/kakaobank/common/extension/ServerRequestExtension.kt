package com.test.kakaobank.common.extension

import com.test.kakaobank.presentation.exception.InvalidParameterException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull

fun ServerRequest.queryParamToIntOrNull(name: String): Int? {
    return queryParamOrNull(name)?.toIntOrNull()
}

fun ServerRequest.queryParamOrThrow(name: String): String {
    return queryParamOrNull(name) ?: throw InvalidParameterException(name)
}