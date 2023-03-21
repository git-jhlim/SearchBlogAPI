package com.test.searchAPI.configuration

import com.test.searchAPI.common.exception.CommonErrorCode
import com.test.searchAPI.common.exception.CommonException
import com.test.searchAPI.common.exception.ErrorResponse
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.attributeOrNull
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import java.time.LocalDateTime

@Component
class CustomErrorAttributes: ErrorAttributes {

    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
        val error = getError(request)
        val status = getHttpStatus(error)
        val errorResponse = getErrorResponse(error)
        val errorAttributes = mutableMapOf<String, Any>()
            .apply {
                this["timestamp"] = LocalDateTime.now()
                this["path"] = request.path()
                this["status"] = status.value()
                this["error"] = status.reasonPhrase
                this["errorCode"] = errorResponse.errorCode
                this["message"] = errorResponse.message
                this["detail"] = errorResponse.detail
            }

        if (options.isIncluded(ErrorAttributeOptions.Include.EXCEPTION)) {
            errorAttributes.remove("exception");
        }

        if (options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }

        if (options.isIncluded(ErrorAttributeOptions.Include.MESSAGE) && errorAttributes["message"] != null) {
            errorAttributes.remove("message");
        }

        if (options.isIncluded(ErrorAttributeOptions.Include.BINDING_ERRORS)) {
            errorAttributes.remove("errors");
        }

        return errorAttributes
    }


    private fun getErrorResponse(error: Throwable): ErrorResponse {
        return when (error) {
            is CommonException -> {
                ErrorResponse(
                    errorCode = error.errorCode.code,
                    message = error.message,
                    detail = error.detail,
                )
            }
            is WebClientResponseException -> {
                if(error.statusCode.is4xxClientError)
                    ErrorResponse(
                        errorCode = CommonErrorCode.BAD_REQUEST.code,
                        message = CommonErrorCode.BAD_REQUEST.getMessage(null),
                    )
                else getInternalErrorRepose()
            }
            else -> getInternalErrorRepose()
        }
    }

    private fun getInternalErrorRepose() = ErrorResponse(
        errorCode = CommonErrorCode.INTERNAL_ERROR.code,
        message = CommonErrorCode.INTERNAL_ERROR.getMessage(null),
    )
    private fun getHttpStatus(error: Throwable): HttpStatus {
        return when (error) {
            is CommonException -> HttpStatus.BAD_REQUEST
            is WebClientResponseException -> {
                if(error.statusCode.is4xxClientError)
                    HttpStatus.BAD_REQUEST
                else HttpStatus.INTERNAL_SERVER_ERROR
            }
            is ResponseStatusException -> {
                if(error.statusCode.is4xxClientError)
                    HttpStatus.BAD_REQUEST
                else HttpStatus.INTERNAL_SERVER_ERROR
            }
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
    }
    override fun getError(request: ServerRequest): Throwable {
        val error = request.attributeOrNull(ErrorAttributes.ERROR_ATTRIBUTE)?.also {
            request.attributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, it)
        } ?: throw IllegalStateException("Missing exception attribute in ServerWebExchange")

        return error as Throwable
    }

    override fun storeErrorInformation(error: Throwable, exchange: ServerWebExchange) {
        exchange.attributes.putIfAbsent(DefaultErrorAttributes.ERROR_ATTRIBUTE, error)
    }
}