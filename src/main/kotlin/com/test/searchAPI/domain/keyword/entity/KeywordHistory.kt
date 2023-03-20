package com.test.searchAPI.domain.keyword.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "user_search_keyword_history")
@Entity
class KeywordHistory (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    val seq: Int = 0,

    @Column(name = "keyword_no")
    val keywordNo: Int,

    @Column(name = "search_date")
    val searchDate: LocalDateTime = LocalDateTime.now(),
)