package com.test.searchAPI.common.exception

interface ErrorCode {
    val code: String
    val messageKey: String
    fun getMessage(arg: String?): String
}