package io.study.io.study.coroutine

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository


@R2dbcRepository(dialect = Dialect.POSTGRES)
interface PersonRepository : CoroutineCrudRepository<Person, Long>