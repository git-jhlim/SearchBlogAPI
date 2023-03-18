package com.test.kakaobank.common.model

import com.test.kakaobank.domain.kakao.model.KakaoSearchResponse

data class PageResponse<T>(
    val totalCount: Int,
    val isEndPage: Boolean,
    val contents: List<T>,
) {
    companion object {

        fun <T, V> convert(kakaoSearchResponse: KakaoSearchResponse<T>, transform: (T) -> V): PageResponse<V> {
            return PageResponse(
                totalCount = kakaoSearchResponse.meta.total_count,
                isEndPage = kakaoSearchResponse.meta.is_end,
                contents = kakaoSearchResponse.documents.map { transform(it) },
            )
        }
    }
}