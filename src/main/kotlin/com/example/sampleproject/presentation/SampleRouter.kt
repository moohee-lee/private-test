package com.example.sampleproject.presentation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class SampleRouter(private val sampleHandler: SampleHandler) {
    @Bean
    fun routerSample(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/sample").nest {
                GET("/hello", sampleHandler::hello)
            }
        }
    }
}
