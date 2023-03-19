package com.test.searchAPI.domain.keyword.entity

import jakarta.persistence.*

@Table(name = "popular_keyword")
@Entity
class PopularKeyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_no")
    val keywordNo: Int,

    @Column(name = "keyword")
    val keyword: String,

) {
    constructor(keyword: String) : this(0,  keyword)

    @Column(name = "search_cnt")
    var searchCount: Int = 1
    fun addSearchCount() {
        searchCount += 1
    }
}