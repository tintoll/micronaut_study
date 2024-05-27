package io.study.coroutine.event

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Serdeable
data class EventRequest(
    var id : Long? = null,
    @field:NotBlank
    val name: String,
    val description: String? = null,
    @field:NotNull
    val projectId: Long,
) {
    fun toEvent(): Event {
        return Event(
            id = id,
            name = name,
            description = description,
            projectId = projectId,
        )
    }
}
