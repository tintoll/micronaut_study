package io.study.coroutine.user

import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface UserRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findAllByOrgId(orgId: Long): List<User>

    @Query("SELECT * FROM public.user WHERE id IN (:ids)")
    suspend fun findByIdIn(ids: List<Long>): List<User>
}