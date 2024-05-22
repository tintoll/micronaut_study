package io.study.coroutine.project.application.service

import io.study.coroutine.project.application.port.ProjectRepository
import io.study.coroutine.project.application.port.ProjectService
import io.study.coroutine.project.domain.Project
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import java.util.*


@Singleton
open class ProjectServiceImpl(private val projectRepository: ProjectRepository) : ProjectService {

    @Transactional
    override suspend fun addProject(project: Project): Project {
        project.createdId = 1L
        project.apiKey = UUID.randomUUID().toString()
        project.secretKey = UUID.randomUUID().toString()
        project.timezone = "Asia/Seoul"

        return projectRepository.save(project)
    }
}