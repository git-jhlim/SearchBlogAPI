package com.test.searchAPI.common.cache

import java.time.Duration

enum class CacheType(val limitCnt: Long, val epriredTime: Duration) {
    KEYWORD(100L, Duration.ofSeconds(20))
}