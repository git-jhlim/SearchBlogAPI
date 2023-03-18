package com.test.kakaobank.common.extension

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull

fun ServerRequest.queryParamToIntOrNull(name: String): Int? {
    return queryParamOrNull(name)?.toIntOrNull()
}


//inline fun <reified T>ServerRequest.requestParamToModel(): T {
//    return queryParams().requestParamToModel()
//}
//
//inline fun <reified T> Map<String,List<String>>.requestParamToModel(): T {
//    val paramMap = mutableMapOf<String, Any>()
//    val getValue:(String) -> Any? = {value: String ->
//        val values = value.split(",")
//        val size = values.size
//
//        when {
//            size == 1 -> value.takeIf { it.isNotBlank() }
//            size > 1 -> values.toSet()
//            else -> null
//        }
//
//        map { entry ->
//            val key = entry.key.split(".")
//            val value = entry.value?.let {
//                getValue(it.filterNotNull().joinToString(","))
//            }
//            if(value != null) {
//                paramMap.addQeuryParamToMap(key, value)
//            }
//
//        }
//
//
//
//    }
//}