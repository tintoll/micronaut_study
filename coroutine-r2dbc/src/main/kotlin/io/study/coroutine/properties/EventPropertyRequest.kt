package io.study.coroutine.properties

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class EventPropertyRequest (
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val projectId: Long,
    val status: PropertyStatus = PropertyStatus.VISIBLE,
    val dataType: PropertyDataType = PropertyDataType.STRING,
) {
    fun toEventProperty(): EventProperty {
        return EventProperty(
            id = id,
            name = name,
            description = description,
            projectId = projectId,
            status = status,
            dataType = dataType,
        )
    }
}