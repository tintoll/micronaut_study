package io.study.coroutine.test

import io.micronaut.data.annotation.Join
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import kotlinx.coroutines.flow.Flow

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface CountryRepository : CoroutineCrudRepository<Country, Long> {

    @Join("countryRegion")
    override fun findAll(): Flow<Country>

    @Join("countryRegion")
    suspend fun findAllByCountryRegionId(id: Long): List<Country>
}
