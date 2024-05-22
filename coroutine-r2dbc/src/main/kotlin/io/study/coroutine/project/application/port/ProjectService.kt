package io.study.coroutine.project.application.port

import io.study.coroutine.project.domain.Project

interface ProjectService {
    suspend fun addProject(project: Project): Project
}