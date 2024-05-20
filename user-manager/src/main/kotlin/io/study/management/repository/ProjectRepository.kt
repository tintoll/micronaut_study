package io.study.management.repository

import io.micronaut.data.annotation.Id
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import io.study.management.domain.Project
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotBlank
import reactor.core.publisher.Mono

@R2dbcRepository(dialect = Dialect.POSTGRES)
abstract class ProjectRepository : ReactorPageableRepository<Project, Long> {

    abstract fun save(@NotBlank name: String): Mono<Project>

    @Transactional
    open fun saveWithException(@NotBlank name: String): Mono<Project> {
        return save(name).then(Mono.error(DataAccessException("test exception")))
    }

    abstract fun update(@Id id: Long, @NotBlank name: String): Mono<Long>

}