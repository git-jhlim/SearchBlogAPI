package com.test.searchAPI.common.exception

import java.time.LocalDateTime

data class ErrorResponse (
    val errorCode: String,
    val time: LocalDateTime = LocalDateTime.now(),
    val message: String = "",
    val detail: Map<String, Any> = emptyMap(),
)