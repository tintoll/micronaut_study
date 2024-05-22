package io.study.coroutine.common

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Error
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.hateoas.Link
import jakarta.inject.Singleton

@Singleton
@Requires(classes = [Exception::class])
class GlobalErrorHandler {

    @Error(global = true)
    fun handleGlobalError(request: HttpRequest<*>, e: Throwable): HttpResponse<JsonError> {
        val error = JsonError("Bad Things Happened: ${e.message}")
            .link(Link.SELF, Link.of(request.uri))

        return HttpResponse.serverError<JsonError>()
            .body(error)
    }
}