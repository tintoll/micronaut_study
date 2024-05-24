package io.study.coroutine.project.application.service

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ResponseUser(
    val id: Long,
    val name: String,
    val email: String,
    val orgId: Long,
    val password : String,
    val imageUrl : String? = null,
)
