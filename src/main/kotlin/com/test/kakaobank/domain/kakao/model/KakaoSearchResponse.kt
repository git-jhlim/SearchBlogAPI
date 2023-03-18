package com.test.kakaobank.domain.kakao.model

data class KakaoSearchResponse<T>(
    val meta: PageMetaInfo,
    val documents: List<T>,
) {

    data class PageMetaInfo(
        val total_count: Int = 0,
        val pageable_count: Int = 0,
        val is_end: Boolean = true,
    )
}