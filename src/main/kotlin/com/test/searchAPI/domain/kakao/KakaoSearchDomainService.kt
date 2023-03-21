package com.test.searchAPI.domain.kakao

import com.test.searchAPI.common.enum.SearchBaseType
import com.test.searchAPI.common.exception.BadRequestException
import com.test.searchAPI.common.extension.pTypeRef
import com.test.searchAPI.common.extension.toModel
import com.test.searchAPI.common.model.BlogPageResponse
import com.test.searchAPI.domain.kakao.exception.KakaoExceptionResponse
import com.test.searchAPI.domain.kakao.model.KakaoBlogSearchParams
import com.test.searchAPI.domain.kakao.model.KakaoBlog
import com.test.searchAPI.domain.kakao.model.KakaoSearchResponse
import com.test.searchAPI.infrastructure.KakaoApiProperties
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

/**
 * 카카오 블로그 검색
 * https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog
 */
@Service
class KakaoSearchDomainService(
    val webClient: WebClient,
    val properties: KakaoApiProperties,
) {
    suspend fun searchBlog(params: KakaoBlogSearchParams): BlogPageResponse<KakaoBlog> {
        return getMonoResult<KakaoSearchResponse<KakaoBlog>>(properties.getBlogSearchUri(params.toMap()))
            .let {
                BlogPageResponse(
                    totalCount = it.meta.total_count,
                    page = params.page,
                    contents = it.documents,
                    baseOn = SearchBaseType.KAKAO,
                )
            }
    }

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
                            throw BadRequestException(
                                mapOf(ERROR_INFO to (it.responseBodyAsString.toModel<KakaoExceptionResponse>()))
                            )
                        }
                    }
                }
            }.awaitSingle()
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val ERROR_INFO = "info"
    }
}