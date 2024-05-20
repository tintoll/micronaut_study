package io.study.management.controller

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank

@Serdeable
data class ProjectUpdateCommand(
    val id : Long,
    @field:NotBlank val name : String
)
