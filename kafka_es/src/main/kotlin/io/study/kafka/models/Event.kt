package io.study.kafka.models

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Event(
    val projectId: Long,
    val eventType: String,
    val eventTime: Long,
    val deviceId: String,
    val userId: String? = null,
    var eid: Long? = null,
)