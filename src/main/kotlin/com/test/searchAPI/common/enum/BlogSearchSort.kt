package com.test.searchAPI.common.enum

enum class BlogSearchSort(val kakao: KaKaoSort, val naver: NaverSort) {
    RECENCY(KaKaoSort.RECENCY, NaverSort.DATE),
    ACCURACY(KaKaoSort.ACCURACY, NaverSort.SIM),
    ;

    companion object {
        private val mapByName = values().associateBy { it.name }

        fun getBy(name: String) = mapByName[name]
    }
}

enum class KaKaoSort(val value: String) {
    RECENCY("recency"),
    ACCURACY("accuracy"),
    ;
}

enum class NaverSort(val value: String) {
    DATE("date"), // 최신순
    SIM("sim"), //정확도순
    ;
}