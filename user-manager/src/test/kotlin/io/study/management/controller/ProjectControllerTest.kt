package io.study.management.controller

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.study.management.domain.Project
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


@MicronautTest(transactional = false)
class ProjectControllerTest(@Client("/") val client: HttpClient) {

    @Test
    fun testFindNonExistingGenreReturn404() {
        val thrown = assertThrows<HttpClientResponseException>{
            client.toBlocking().exchange<Any>("/projects/99")
        }

        assertNotNull(thrown.response)
        assertEquals(HttpStatus.NOT_FOUND, thrown.status)
    }

    @Test
    fun testGenreCrudOperations() {

        var request = HttpRequest.POST("/projects", mapOf("name" to "DevOps"))
        var response = client.toBlocking().exchange(request, Project::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertEquals("/projects/1", response.header(HttpHeaders.LOCATION))

        request = HttpRequest.POST("/projects", mapOf("name" to "Microservices"))
        response = client.toBlocking().exchange(request, Project::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertEquals("/projects/2", response.header(HttpHeaders.LOCATION))

        var genre = client.toBlocking().retrieve("/projects/2", Project::class.java)

        assertEquals("Microservices", genre.name)

        var cmdRequest = HttpRequest.PUT("/projects", ProjectUpdateCommand(2, "Micro-services"))
        response = client.toBlocking().exchange(cmdRequest)

        assertEquals(HttpStatus.NO_CONTENT, response.status())

        genre = client.toBlocking().retrieve("/projects/2", Project::class.java)

        assertEquals("Micro-services", genre.name)

        request = HttpRequest.GET("/projects/list")
        var genres = client.toBlocking().retrieve(request, Argument.listOf(Project::class.java))

        assertEquals(2, genres.size)

        request = HttpRequest.POST("/projects/ex", mapOf("name" to "Microservices"))
        response = client.toBlocking().exchange(request)

        assertEquals(HttpStatus.NO_CONTENT, response.status)

        request = HttpRequest.GET("/projects/list")
        genres = client.toBlocking().retrieve(request, Argument.listOf(Project::class.java))

        assertEquals(2, genres.size)

        request = HttpRequest.GET("/projects/list?size=1")
        genres= client.toBlocking().retrieve(request, Argument.listOf(Project::class.java))

        assertEquals(1, genres.size)
        assertEquals("DevOps", genres[0].name)

        request = HttpRequest.GET("/projects/list?size=1&sort=name,desc")
        genres= client.toBlocking().retrieve(request, Argument.listOf(Project::class.java))

        assertEquals(1, genres.size)
        assertEquals("Micro-services", genres[0].name)

        request = HttpRequest.GET("/projects/list?size=1&page=2")
        genres= client.toBlocking().retrieve(request, Argument.listOf(Project::class.java))

        assertEquals(0, genres.size)

        for (i in 1..2) {
            request = HttpRequest.DELETE("/projects/$i")
            response = client.toBlocking().exchange(request)

            assertEquals(HttpStatus.NO_CONTENT, response.status)
        }

        request = HttpRequest.GET("/projects/list")
        genres = client.toBlocking().retrieve(request, Argument.listOf(Project::class.java))

        assertEquals(0, genres.size)

    }

}