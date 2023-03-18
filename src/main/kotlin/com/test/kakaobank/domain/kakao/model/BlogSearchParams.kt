package com.test.kakaobank.domain.kakao.model

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

data class BlogSearchParams (
    val query: String,
    val sort: String,
    val page: Int,
    val size: Int,
) {
    fun toMap(): MultiValueMap<String, String> {
        val queryParamMap = LinkedMultiValueMap<String,String>()

        javaClass.declaredFields.forEach { property ->
            queryParamMap[property.name] = property.get(this).toString()
        }

        return queryParamMap
    }
}