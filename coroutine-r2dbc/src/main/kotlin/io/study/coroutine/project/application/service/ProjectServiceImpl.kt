package io.study.coroutine.project.application.service

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Error
import io.micronaut.http.client.HttpClient
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.hateoas.Link
import io.micronaut.http.uri.UriBuilder
import io.study.coroutine.common.ProjectNotFoundException
import io.study.coroutine.project.application.port.ProjectRepository
import io.study.coroutine.project.application.port.ProjectService
import io.study.coroutine.project.domain.Project
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import kotlinx.coroutines.reactive.awaitSingle
import java.util.*


@Singleton
open class ProjectServiceImpl(
    private val projectRepository: ProjectRepository,
    private val httpClient: HttpClient,
) : ProjectService {


    @Transactional
    override suspend fun addProject(project: Project): Project {
        project.createdId = 1L
        project.apiKey = UUID.randomUUID().toString()
        project.secretKey = UUID.randomUUID().toString()
        project.timezone = "Asia/Seoul"
        project.joinUsers = listOf(project.createdId!!)

        return projectRepository.save(project)
    }

    override suspend fun getProjects(orgId: Long): List<Project> {
        return projectRepository.findByOrgId(orgId)
    }

    override suspend fun getProject(id: Long): Project {
        return projectRepository.findById(id) ?: throw ProjectNotFoundException("존재하지 않는 프로젝트입니다. id: $id")
    }

    @Transactional
    override suspend fun deleteProject(id: Long) {
        projectRepository.deleteById(id)
    }

    @Transactional
    override suspend fun updateProject(id: Long, editProject: Project): Project {
        val storeProject = getProject(id)
        val editedProject = storeProject.copy(
            name = editProject.name,
            description = editProject.description,
            timezone = editProject.timezone
        )

        return projectRepository.update(editedProject)
    }

    @Transactional
    override suspend fun updateProjectName(id: Long, name: String): Int {
        return projectRepository.updateName(id, name)
    }

    @Transactional
    override suspend fun updateProjectTimezone(id: Long, timezone: String): Int {
        return projectRepository.updateTimezone(id, timezone)
    }


    override suspend fun getProjectUsers(id: Long): List<ResponseUser> {
        val project = getProject(id)

        val uriBuilder = UriBuilder.of("http://localhost:8200/users/ids")
        project.joinUsers?.forEach {
            uriBuilder.queryParam("ids", it)
        }

        val uri = uriBuilder.build().toString()
        val req = HttpRequest.GET<ResponseUser>(uri)
        val result = httpClient.retrieve(req, Argument.listOf(ResponseUser::class.java))
        val users: MutableList<ResponseUser> = result.awaitSingle()
        return users.toList()
    }

    @Transactional
    override suspend fun addUsers(id: Long, users: List<Long>): Int {
        val storeProject = getProject(id)
        val editUsers = storeProject.joinUsers!! + users
        projectRepository.updateJoinUsers(id, editUsers.toSet())
        return users.size
    }
}