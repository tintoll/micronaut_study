package io.study.coroutine.project.adapter

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.study.coroutine.project.application.port.ProjectService
import io.study.coroutine.project.domain.Project

@Controller("/projects")
class ProjectAPI(private val projectService: ProjectService) {

    @Post
    suspend fun addProject(@Body projectRequest: ProjectRequest) = projectService.addProject(projectRequest.toProject())
}
