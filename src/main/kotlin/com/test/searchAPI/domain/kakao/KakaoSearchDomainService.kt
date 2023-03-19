package com.test.searchAPI.domain.kakao

import com.test.searchAPI.common.exception.BadRequestException
import com.test.searchAPI.common.extension.toModel
import com.test.searchAPI.domain.kakao.exception.KakaoExceptionResponse
import com.test.searchAPI.domain.kakao.model.BlogSearchParams
import com.test.searchAPI.domain.kakao.model.KakaoBlog
import com.test.searchAPI.domain.kakao.model.KakaoSearchResponse
import com.test.searchAPI.infrastructure.KakaoApiProperties
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

@Service
class KakaoSearchDomainService(
    val webClient: WebClient,
    val properties: KakaoApiProperties,
) {
    suspend fun searchBlog(params: BlogSearchParams): KakaoSearchResponse<KakaoBlog> {
        return getMonoResult(properties.getBlogSearchUri(params.toMap()))
    }

    private inline fun <reified T> pTypeRef() = object: ParameterizedTypeReference<T>() {}
    private suspend inline fun <reified T> getMonoResult(uriParam: String): T {
        return webClient
            .get()
            .uri(uriParam)
            .header(AUTHORIZATION, properties.getAppkey())
            .retrieve()
            .bodyToMono(pTypeRef<T>())
            .doOnError {
                when (it) {
                    is WebClientResponseException -> {
                        if (it.statusCode.is5xxServerError)  throw it

                        if (it.statusCode.is4xxClientError) {
                            throw BadRequestException(mapOf("info" to (it.responseBodyAsString.toModel<KakaoExceptionResponse>())))
                        }
                    }
                }
            }.awaitSingle()
    }
    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}