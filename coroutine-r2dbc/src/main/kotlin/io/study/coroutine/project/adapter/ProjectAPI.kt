package io.study.coroutine.project.adapter

import io.github.oshai.kotlinlogging.KotlinLogging
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.study.coroutine.organization.adapter.AddUsersRequest
import io.study.coroutine.project.application.port.ProjectService
import io.study.coroutine.project.domain.Project

@Controller("/projects")
class ProjectAPI(private val projectService: ProjectService) {

    @Get
    suspend fun getProjects(@Parameter orgId: Long) = projectService.getProjects(orgId)

    @Get("/{id}")
    suspend fun getProject(@PathVariable id: Long) = projectService.getProject(id)

    @Post
    suspend fun addProject(@Body projectRequest: ProjectRequest) = projectService.addProject(projectRequest.toProject())

    @Delete("/{id}")
    suspend fun deleteProject(@PathVariable id: Long) = projectService.deleteProject(id)

    // Mixpanel, Amplitude 는  전체 업데이트를 하지 않고 수정가능한 요소들만 수정하도록 되어 있습니다.
    @Put("/{id}")
    suspend fun updateProject(@PathVariable id: Long, @Body projectRequest: ProjectRequest): Project {
        projectRequest.id = id
        return projectService.updateProject(id, projectRequest.toProject())
    }

    @Post("/{id}/update/name")
    suspend fun updateProjectName(@PathVariable id: Long, @Body(value = "name") name: String): Int {
        return projectService.updateProjectName(id, name)
    }

    @Post("/{id}/update/timezone")
    suspend fun updateProjectTimezone(@PathVariable id: Long, @Body(value = "timezone") timezone: String): Int {
        return projectService.updateProjectTimezone(id, timezone)
    }

    @Get("/{id}/users")
    suspend fun getProjectUsers(@PathVariable id: Long) = projectService.getProjectUsers(id)

    @Post("/{id}/add-users")
    suspend fun addUsers(@PathVariable id: Long, @Body(value = "users") users: List<Long>): Map<String, Int>{
        return mapOf("addUsers" to projectService.addUsers(id, users))
    }
}
