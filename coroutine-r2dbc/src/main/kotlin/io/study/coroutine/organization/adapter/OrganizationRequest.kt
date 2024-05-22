package io.study.coroutine.organization.adapter

import io.micronaut.serde.annotation.Serdeable
import io.study.coroutine.organization.domain.Organization


@Serdeable
data class OrganizationRequest(
    val name: String,
    val description: String? = null,
) {
    fun toOrganization() = Organization(name=name, description = description)
}
