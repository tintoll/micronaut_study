package io.study.coroutine.organization.adapter

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AddUsersRequest(
    val projectId : Long,
    val users: List<Long>
)