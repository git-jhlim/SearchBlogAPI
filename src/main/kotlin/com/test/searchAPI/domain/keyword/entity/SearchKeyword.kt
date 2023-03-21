package com.test.searchAPI.domain.keyword.entity

import jakarta.persistence.*

@Table(
    name = "user_search_keyword",
    indexes = [ Index(name = "idx_keyword", columnList = "keyword") ]
)
@Entity
class SearchKeyword (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_no")
    val keywordNo: Int = 0,

    @Column(name = "keyword", length = 250)
    val keyword: String,
)