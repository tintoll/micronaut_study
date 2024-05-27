package io.study.coroutine.event

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@MappedEntity
data class Event(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val projectId: Long,
    val activity: EventActivity = EventActivity.ACTIVE,
    val type: EventType = EventType.DEFINED,
)
