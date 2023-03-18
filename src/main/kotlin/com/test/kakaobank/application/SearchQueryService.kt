package com.test.kakaobank.application

import com.test.kakaobank.application.model.BlogSearchModel
import org.springframework.stereotype.Service

@Service
class SearchQueryService {
    suspend fun searchBlog(searchModel: BlogSearchModel): Int {
        return 0
    }
}