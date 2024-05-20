package io.study.io.study.coroutine

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Person(
    @field:Id val id: Long,
    val name: String,
    val age: Int
)
