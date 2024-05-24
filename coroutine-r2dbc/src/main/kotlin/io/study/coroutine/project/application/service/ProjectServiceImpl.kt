package io.study.coroutine.project.application.service

import io.study.coroutine.project.application.port.ProjectRepository
import io.study.coroutine.project.application.port.ProjectService
import io.study.coroutine.project.domain.Project
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import java.util.*
import kotlin.collections.HashMap


@Singleton
open class ProjectServiceImpl(private val projectRepository: ProjectRepository) : ProjectService {

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
        return projectRepository.findById(id) ?: throw IllegalArgumentException("Project not found for id: $id")
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


    override suspend fun getProjectUsers(id: Long): List<HashMap<String, String>> {
        // 사용자 정보를 가져와서 상세 정보를 user에 요청하여 받은 결과를 리턴

        // 프로젝트에 말고 project_users (id, project_id, user_id, role) 테이블을 만들어서 사용자 정보를 가져오는게 좋을까?
        TODO("Not yet implemented")
    }

    @Transactional
    override suspend fun addUsers(id: Long, users: List<Long>): Int {
        val storeProject = getProject(id)
        val editUsers = storeProject.joinUsers!! + users
        projectRepository.updateJoinUsers(id, editUsers.toSet())
        return users.size
    }
}