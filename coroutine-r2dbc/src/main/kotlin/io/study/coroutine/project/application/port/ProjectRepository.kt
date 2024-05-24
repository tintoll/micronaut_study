package io.study.coroutine.project.application.port

import io.micronaut.data.annotation.Id
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import io.study.coroutine.project.domain.Project

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface ProjectRepository : CoroutineCrudRepository<Project, Long> {
    suspend fun findByOrgId(orgId: Long): List<Project>
    suspend fun updateName(@Id id: Long, name: String): Int
    suspend fun updateTimezone(@Id id: Long, timezone: String): Int
    suspend fun updateJoinUsers(@Id id: Long, joinUsers : Set<Long>): Int
}