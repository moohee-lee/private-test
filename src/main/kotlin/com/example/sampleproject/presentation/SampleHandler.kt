package com.example.sampleproject.presentation

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class SampleHandler {
    suspend fun hello(request: ServerRequest): ServerResponse {
        return ok().bodyValueAndAwait("world")
    }
}
