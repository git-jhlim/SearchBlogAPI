package com.test.searchAPI.common.enum

enum class BlogSearchSort(val value: String) {
    RECENCY("recency"), // 최신순
    ACCURACY("accuracy"), // 정확도순
    ;

    companion object {
        private val mapByName = values().associateBy { it.name }

        fun getBy(name: String) = mapByName[name]
    }
}