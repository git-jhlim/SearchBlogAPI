package com.test.searchAPI.domain.naver

import com.test.searchAPI.common.enum.SearchBaseType
import com.test.searchAPI.common.exception.BadRequestException
import com.test.searchAPI.common.extension.pTypeRef
import com.test.searchAPI.common.extension.toModel
import com.test.searchAPI.common.model.BlogPageResponse
import com.test.searchAPI.domain.naver.model.NaverBlog
import com.test.searchAPI.domain.naver.model.NaverBlogSearchParams
import com.test.searchAPI.domain.naver.model.NaverSearchResponse
import com.test.searchAPI.infrastructure.NaverApiProperties
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import kotlin.math.ceil

@Service
/**
 * 네이버 블로그 검
 * https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EB%B8%94%EB%A1%9C%EA%B7%B8-%EA%B2%80%EC%83%89-api-%EB%A0%88%ED%8D%BC%EB%9F%B0%EC%8A%A4
 */
class NaverSearchDomainService(
    private val webClient: WebClient,
    private val properties: NaverApiProperties,
) {
    suspend fun searchBlog(params: NaverBlogSearchParams): BlogPageResponse<NaverBlog> {
        return getMonoResult<NaverSearchResponse<NaverBlog>>(properties.getBlogSearchUri(params.toMap()))
            .let {
                val totalPages = ceil((it.total.toDouble()) / (params.display.toDouble())).toInt()
                BlogPageResponse(
                    page = it.start,
                    pageSize = params.display,
                    totalPages = totalPages,
                    totalCount = it.total,
                    hasNext = it.start < totalPages,
                    hasPrevious = it.start > 1,
                    contents = it.items,
                    baseOn = SearchBaseType.NAVER,
                )
            }
    }

    private suspend inline fun <reified T> getMonoResult(uriParam: String): T {
        return webClient
            .get()
            .uri(uriParam)
            .headers{
                it[CLIENT_ID] = properties.clientId
                it[CLIENT_SECRET] = properties.clientSecret
            }
            .retrieve()
            .bodyToMono(pTypeRef<T>())
            .doOnError {
                when (it) {
                    is WebClientResponseException -> {
                        if (it.statusCode.is5xxServerError)
                            throw it

                        if (it.statusCode.is4xxClientError) {
                            throw BadRequestException(
                                mapOf(ERROR_INFO to ( it.responseBodyAsString.toModel<NaverExceptionResponse>()))
                            )
                        }
                    }
                }
            }.awaitSingle()
    }

    companion object {
        private const val CLIENT_ID = "X-Naver-Client-Id"
        private const val CLIENT_SECRET = "X-Naver-Client-Secret"
        private const val ERROR_INFO = "info"
    }
}