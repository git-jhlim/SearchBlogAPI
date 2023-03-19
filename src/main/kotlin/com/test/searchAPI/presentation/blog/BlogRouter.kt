package com.test.searchAPI.presentation.blog

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

@Configuration
class BlogRouter(private val blogHandler: BlogHandler) {
    @Bean
    fun coBlogRouter(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/blog/search").nest {
                GET("", blogHandler::searchBlog)
            }
        }
    }
}