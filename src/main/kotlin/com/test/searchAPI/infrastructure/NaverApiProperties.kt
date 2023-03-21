package com.test.searchAPI.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

@ConfigurationProperties(prefix = "naver.api")
class NaverApiProperties @ConstructorBinding constructor(
    val protocol: String,
    val host: String,
    val clientId: String,
    val clientSecret: String,
    val blogSearch: ApiPath,
) {
    fun getBlogSearchUri(params: MultiValueMap<String, String>): String {
        return UriComponentsBuilder.newInstance()
            .scheme(protocol)
            .host(host)
            .path(blogSearch.path)
            .queryParams(params)
            .build()
            .toUriString()
    }
}