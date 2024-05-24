package io.study.coroutine.organization.application.port

import io.study.coroutine.organization.adapter.AddUsersRequest
import io.study.coroutine.organization.domain.Organization


interface OrganizationService {
    suspend fun getOrganization(id: Long): Organization?
    suspend fun addOrganization(organization: Organization): Organization
    suspend fun updateOrganization(id: Long, organization: Organization): Organization
    suspend fun deleteOrganization(id: Long)
    suspend fun addUsersToProject(id: Long, addUsersRequest: AddUsersRequest): Map<String, Int>
}