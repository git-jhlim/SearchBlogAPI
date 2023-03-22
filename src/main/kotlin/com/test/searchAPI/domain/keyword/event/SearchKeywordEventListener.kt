package com.test.searchAPI.domain.keyword.event

import com.test.searchAPI.common.cache.CacheType
import com.test.searchAPI.common.cache.LocalCacheManager
import com.test.searchAPI.domain.keyword.entity.SearchKeyword
import com.test.searchAPI.domain.keyword.entity.KeywordHistory
import com.test.searchAPI.domain.keyword.repository.KeywordHistoryRepository
import com.test.searchAPI.domain.keyword.repository.SearchKeywordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SearchKeywordEventListener(
    private val searchKeywordRepository: SearchKeywordRepository,
    private val keywordHistoryRepository: KeywordHistoryRepository,
) {
    private val mutex = Mutex()

    @EventListener(SearchKeywordEvent::class)
    @Transactional
    fun addKeywordSearchCount(event: SearchKeywordEvent) {
        runBlocking {
            mutex.withLock {
                LocalCacheManager.getValue(CacheType.KEYWORD, event.keyword) {
                    withContext(Dispatchers.IO) {
                        searchKeywordRepository.findByKeyword(it)
                            ?: searchKeywordRepository.save(SearchKeyword(keyword = it))
                    }
                }
            }.run { keywordHistoryRepository.save(KeywordHistory(keywordNo = this.keywordNo)) }
        }
    }
}