package io.study.coroutine.common

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton


@Singleton
class CustomExceptionHandler : ExceptionHandler<ProjectNotFoundException, HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, exception: ProjectNotFoundException): HttpResponse<Any> {
        return HttpResponse
            .notFound<Any>()
            .body(
                mapOf(
                    "message" to exception.message
                )
            )
    }
}