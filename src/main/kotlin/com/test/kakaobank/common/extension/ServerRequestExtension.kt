package com.test.kakaobank.common.extension

import com.test.kakaobank.presentation.exception.InvalidParameterException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull

fun ServerRequest.queryParamToPositiveIntOrNull(name: String): Int? {
    return queryParamOrNull(name)?.toIntOrNull()?.also { if(it <= 0) throw InvalidParameterException(name) }
}

fun ServerRequest.queryParamOrThrow(name: String): String {
    return queryParamOrNull(name) ?: throw InvalidParameterException(name)
}