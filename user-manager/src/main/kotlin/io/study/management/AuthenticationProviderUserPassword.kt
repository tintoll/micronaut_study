package io.study.management

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider

class AuthenticationProviderUserPassword<B> : HttpRequestAuthenticationProvider<B> {
    override fun authenticate(
        requestContext: HttpRequest<B>?,
        authRequest: AuthenticationRequest<String, String>
    ): AuthenticationResponse {
        return if (authRequest.identity.equals("sherlock") && authRequest.secret.equals("password")) AuthenticationResponse.success(
            authRequest.identity
        ) else AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
    }
}