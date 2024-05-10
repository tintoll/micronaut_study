package io.study.management

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

@MicronautTest
class JwtAuthenticationTest {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Test
    fun accessingASecuredUrlWithoutAuthenticatingReturnsUnauthorized() {
        val e = assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking().exchange<Any, Any>(
                HttpRequest.GET<Any>("/").accept(MediaType.TEXT_PLAIN)
            )
        }

        assertEquals(HttpStatus.UNAUTHORIZED, e.status)
    }
}