package com.test.searchAPI.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder



@ConfigurationProperties(prefix = "kakao.api")
data class KakaoApiProperties @ConstructorBinding constructor(
    val protocol: String,
    val host: String,
    private val appkey: String,
    val blogSearch: ApiPath,
) {
    data class ApiPath(val path: String)

    fun getBlogSearchUri(params: MultiValueMap<String, String>): String {
        return UriComponentsBuilder.newInstance()
            .scheme(protocol)
            .host(host)
            .path(blogSearch.path)
            .queryParams(params)
            .build()
            .toUriString()
    }

    fun getAppkey() = "$KAKAO_AUTH_HEADER $appkey"
    companion object {
        private const val KAKAO_AUTH_HEADER = "KakaoAK"
    }
}