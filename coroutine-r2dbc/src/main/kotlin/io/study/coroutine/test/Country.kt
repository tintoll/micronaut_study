package io.study.coroutine.test

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity
data class Country(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    val id: Long? = null,
    val name: String,

    @field:Relation(value = Relation.Kind.ONE_TO_ONE)
    val countryRegion: CountryRegion
)


@Serdeable
@MappedEntity
data class CountryRegion(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    val id: Long,
    val name: String

)