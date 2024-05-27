package io.study.coroutine.common

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import jakarta.validation.ConstraintViolationException

@Produces
@Singleton
@Requires(classes = [ConstraintViolationException::class, ExceptionHandler::class])
class GlobalExceptionHandler : ExceptionHandler<ConstraintViolationException, HttpResponse<*>> {

    override fun handle(request: HttpRequest<*>, exception: ConstraintViolationException): HttpResponse<*> {
        val violations = exception.constraintViolations
        val details = violations.map { "${it.propertyPath}: ${it.message}" }
        val response = CustomErrorResponse(
            error = "Validation Failed",
            details = details
        )
        return HttpResponse.badRequest(response)
    }
}

data class CustomErrorResponse(
    val error: String,
    val details: List<String>
)