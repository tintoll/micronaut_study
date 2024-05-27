package io.study.coroutine.common

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Error
import jakarta.inject.Singleton

@Singleton
@Requires(classes = [Exception::class])
class GlobalErrorHandler {

    @Error(global = true)
    fun handleGlobalError(request: HttpRequest<*>, e: Throwable): HttpResponse<Any> {
        return HttpResponse.serverError<Any>()
            .body(mapOf("message" to e.message) )
    }
}