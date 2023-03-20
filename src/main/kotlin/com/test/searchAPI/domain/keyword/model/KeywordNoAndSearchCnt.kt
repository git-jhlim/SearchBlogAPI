package com.test.searchAPI.domain.keyword.model

import com.querydsl.core.annotations.QueryProjection

data class KeywordNoAndSearchCnt
@QueryProjection constructor(
    val keywordNo: Int,
    val searchCnt: Long,
)