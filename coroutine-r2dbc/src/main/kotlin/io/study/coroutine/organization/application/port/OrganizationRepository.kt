package io.study.coroutine.organization.application.port

import io.micronaut.data.annotation.Id
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import io.study.coroutine.organization.domain.Organization
import jakarta.inject.Named

@R2dbcRepository(dialect = Dialect.POSTGRES)
//@Named("secondary")
interface OrganizationRepository : CoroutineCrudRepository<Organization, Long> {
    suspend fun update(@Id id:Long, name: String, description: String? = null)
}