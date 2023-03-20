package com.test.searchAPI.domain.keyword.repository

import com.test.searchAPI.domain.keyword.entity.KeywordHistory
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordHistoryRepository: JpaRepository<KeywordHistory, Int>