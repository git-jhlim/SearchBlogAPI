package com.test.searchAPI.common.cache

import com.github.benmanes.caffeine.cache.AsyncCache
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.coroutines.*
import kotlinx.coroutines.future.asDeferred
import kotlinx.coroutines.future.future

object LocalCacheManager {
    private class LocalCache<K,V>(private val cache: AsyncCache<K,V>) {
        suspend fun get(key: K, loadData: suspend (K) -> V): Deferred<V> {
            return coroutineScope {
                cache.get(key) { k,_ ->
                    CoroutineScope(Dispatchers.IO).future { loadData(k) }
                }
            }.asDeferred()
        }
    }

    private val caches = CacheType.values().associateWith {
        LocalCache(
            cache = Caffeine.newBuilder()
                .maximumSize(it.limitCnt)
                .expireAfterWrite(it.epriredTime)
                .buildAsync<Any,Any>()
        )
    }

    suspend fun <K,V> getValue(cacheType: CacheType, key: K, loadData: suspend (K) -> V): V {
        return (caches.getValue(cacheType).get(key) { loadData(it as K) }).await() as V
    }
}