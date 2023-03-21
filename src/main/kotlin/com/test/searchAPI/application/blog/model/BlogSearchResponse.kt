package com.test.searchAPI.application.blog.model

import com.test.searchAPI.domain.kakao.model.KakaoBlog
import com.test.searchAPI.domain.naver.model.NaverBlog
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

        fun of(blog: NaverBlog): BlogSearchResponse {
            return BlogSearchResponse(
                blogName = blog.bloggername,
                title = blog.title,
                thumbnail = "",
                blogUrl = blog.link,
                contentSummary = blog.description,
                registerDateTime = blog.postdate.atStartOfDay(),
            )
        }

        private fun String.toRemoveHtml(): String {
            val regex = "<(/)?b>".toRegex()
            return replace(regex, "")
        }
    }
}