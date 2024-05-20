package io.study.management.controller

import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import io.study.management.domain.Project
import io.study.management.repository.ProjectRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import reactor.core.publisher.Mono
import java.net.URI

@Controller("/projects")
open class ProjectController(private val projectRepository: ProjectRepository) {

    @Get("/{id}")
    fun show(id: Long) = projectRepository.findById(id)

    @Put
    open fun update(@Body @Valid command: ProjectUpdateCommand): Mono<HttpResponse<*>> {
        return projectRepository.update(command.id, command.name)
            .thenReturn(
                HttpResponse.noContent<Any>().header(HttpHeaders.LOCATION, location(command.id).path)
            )
    }

    @Get("/list")
    open fun list(@Valid pageable: Pageable) = projectRepository.findAll(pageable).map { it.content }

    @Post
    open fun save(@NotBlank @Body name: String): Mono<HttpResponse<Project>> {
        return projectRepository.save(name).map(this::createProject)
    }

    @Post("/ex")
    open fun saveExceptions(@NotBlank @Body name: String): Mono<MutableHttpResponse<Project>> {
        return projectRepository.saveWithException(name)
            .map(this::createProject)
            .onErrorReturn(HttpResponse.noContent())
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun delete(id: Long) = projectRepository.deleteById(id).then()


    private fun createProject(project: Project) = HttpResponse.created(project).headers { headers ->
        headers.location(location(project))
    }

    private fun location(id: Long?) = URI.create("/projects/$id")
    private fun location(project: Project) = location(project.id)
}