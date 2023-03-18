package com.test.kakaobank.application.model

import com.test.kakaobank.common.enum.BlogSearchSort

data class BlogSearchModel (
    val keyword: String,
    val sorting: BlogSearchSort,
    val page: Int,
    val size: Int,
)