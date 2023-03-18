package com.test.kakaobank.application.model

import com.test.kakaobank.domain.kakao.model.KakaoBlog
import java.time.LocalDateTime

data class BlogSearchResponse (
    val blogName: String,
    val title: String,
    val thumbnail: String,
    val blogUrl: String,
    val contentSummary: String,
    val registerDateTime: LocalDateTime,
) {
    companion object {
        fun of(blog: KakaoBlog) =  BlogSearchResponse(
            blogName = blog.blogname,
            title = blog.title,
            thumbnail = blog.thumbnail,
            blogUrl = blog.url,
            contentSummary = blog.contents,
            registerDateTime = blog.datetime,
        )
    }
}