package com.test.searchAPI.domain.naver.model

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

data class NaverBlogSearchParams(
    val query: String,
    val display: Int,
    val start: Int,
    val sort: String,
) {
    fun toMap(): MultiValueMap<String, String> {
        val queryParamMap = LinkedMultiValueMap<String,String>()

        javaClass.declaredFields.forEach { property ->
            queryParamMap[property.name] = property.get(this).toString()
        }

        return queryParamMap
    }
}