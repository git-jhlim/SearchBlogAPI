package com.test.searchAPI.domain.keyword.event

import com.test.searchAPI.domain.keyword.KeywordDomainService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PopularKeywordEventListener(
    private val keywordDomainService: KeywordDomainService,
) {
    private val mutex = Mutex()
    @EventListener(PopularKeywordEvent::class)
    @Transactional
    fun addKeywordSearchCount(event: PopularKeywordEvent) {
        runBlocking {
            mutex.withLock {
                keywordDomainService.addSearchCount(event.keyword)
            }
        }
    }
}