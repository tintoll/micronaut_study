package io.study.coroutine.project.application.port

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import io.study.coroutine.project.domain.Project

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface ProjectRepository : CoroutineCrudRepository<Project, Long> {
}