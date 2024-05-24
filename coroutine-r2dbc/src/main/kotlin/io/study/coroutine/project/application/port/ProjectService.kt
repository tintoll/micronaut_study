package io.study.coroutine.project.application.port

import io.study.coroutine.project.application.service.ResponseUser
import io.study.coroutine.project.domain.Project

interface ProjectService {
    suspend fun addProject(project: Project): Project
    suspend fun getProjects(orgId: Long): List<Project>
    suspend fun getProject(id: Long): Project
    suspend fun deleteProject(id: Long)

    // 서비스도 나누는게 좋을까? ProjectUpdateService
    suspend fun updateProject(id: Long, editProject: Project): Project
    suspend fun updateProjectName(id: Long, name: String): Int
    suspend fun updateProjectTimezone(id: Long, timezone: String): Int
    suspend fun getProjectUsers(id: Long): List<ResponseUser>
    suspend fun addUsers(id: Long, users: List<Long>): Int
}