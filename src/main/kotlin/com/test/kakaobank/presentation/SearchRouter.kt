package com.test.kakaobank.presentation

import io.netty.handler.codec.http.HttpMethod.GET
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*

//@Configuration
//class SearchRouter(private val searchHandler: SearchHandler) {
//    @Bean
//    fun coSearchRouter(): RouterFunction<ServerResponse> {
//        return coRouter {
//            (accept(MediaType.APPLICATION_JSON) and "/blog/search").nest {
//                GET("", searchHandler::searchBlog)
//            }
//        }
//    }
//}