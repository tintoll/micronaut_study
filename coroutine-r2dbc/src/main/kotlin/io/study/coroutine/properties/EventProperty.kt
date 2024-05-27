package io.study.coroutine.properties

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity
data class EventProperty(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val projectId: Long,
    val status: PropertyStatus = PropertyStatus.VISIBLE,
    val dataType: PropertyDataType = PropertyDataType.STRING,
)