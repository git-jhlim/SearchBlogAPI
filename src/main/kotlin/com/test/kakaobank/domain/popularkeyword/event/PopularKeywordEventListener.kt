package com.test.kakaobank.domain.popularkeyword.event

import com.test.kakaobank.domain.popularkeyword.PopularKeywordService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PopularKeywordEventListener(
    private val popularKeywordService: PopularKeywordService,
) {
    private val mutex = Mutex()
    @EventListener(PopularKeywordEvent::class)
    @Transactional
    fun addKeywordSearchCount(event: PopularKeywordEvent) {
        runBlocking {
            mutex.withLock {
                popularKeywordService.addSearchCount(event.keyword)
            }
        }
    }
}