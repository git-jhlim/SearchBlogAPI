package com.test.searchAPI.infrastructure.database.queryDsl

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.test.searchAPI.domain.keyword.entity.QKeywordHistory
import com.test.searchAPI.domain.keyword.entity.QSearchKeyword
import com.test.searchAPI.domain.keyword.entity.SearchKeyword
import com.test.searchAPI.domain.keyword.model.KeywordNoAndSearchCnt
import com.test.searchAPI.domain.keyword.model.PopularKeyword
import com.test.searchAPI.domain.keyword.repository.SearchKeywordRepositoryCustom
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class SearchKeywordRepositoryImpl: SearchKeywordRepositoryCustom, QuerydslRepositorySupport(SearchKeyword::class.java) {
    private val searchKeyword = QSearchKeyword.searchKeyword
    private val keywordHistory = QKeywordHistory.keywordHistory

    override fun getPopularKeyword(): List<PopularKeyword> {
        val keywordNoAndCounts = getPopularKeywordsTop10()

        val keywordMapByNo = from(searchKeyword)
            .where(searchKeyword.keywordNo.`in`(keywordNoAndCounts.map { it.keywordNo }))
            .fetch()
            .associate { it.keywordNo to it.keyword }

        return keywordNoAndCounts.map {
            PopularKeyword(
                keyword = keywordMapByNo.getValue(it.keywordNo),
                searchCount = it.searchCnt,
            )
        }
    }

    private fun getPopularKeywordsTop10(): List<KeywordNoAndSearchCnt> {
        return from(keywordHistory)
            .select(
                Projections.constructor(
                    KeywordNoAndSearchCnt::class.java,
                    keywordHistory.keywordNo,
                    keywordHistory.keywordNo.count().`as`(COUNT),
                )
            ).groupBy(keywordHistory.keywordNo)
            .orderBy(Expressions.stringPath(COUNT).desc(), keywordHistory.keywordNo.desc())
            .limit(10)
            .fetch()
    }

    companion object {
        private const val COUNT = "count"
    }
}