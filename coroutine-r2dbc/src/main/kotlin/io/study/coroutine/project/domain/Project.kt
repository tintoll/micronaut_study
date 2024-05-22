package io.study.coroutine.project.domain

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity
data class Project(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val orgId: Long,
    var createdId: Long? = null,
    var timezone: String? = null,
    var apiKey: String? = null,
    var secretKey: String? = null,
)