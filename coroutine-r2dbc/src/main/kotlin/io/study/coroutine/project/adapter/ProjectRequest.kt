package io.study.coroutine.project.adapter

import io.micronaut.serde.annotation.Serdeable
import io.study.coroutine.project.domain.Project

@Serdeable
data class ProjectRequest(
    var id: Long? = null,
    val orgId: Long,
    val name: String,
    val description: String? = null,
    val timezone : String? = null,

) {
    fun toProject() = Project(
        id = id,
        name = name,
        description = description,
        orgId = orgId,
        timezone = timezone
    )
}