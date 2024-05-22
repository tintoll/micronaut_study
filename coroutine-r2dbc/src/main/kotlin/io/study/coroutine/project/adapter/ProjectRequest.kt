package io.study.coroutine.project.adapter

import io.micronaut.serde.annotation.Serdeable
import io.study.coroutine.project.domain.Project

@Serdeable
data class ProjectRequest(
    val orgId: Long,
    val name: String,
    val description: String? = null,
) {
    fun toProject() = Project(
        name = name,
        description = description,
        orgId = orgId
    )
}