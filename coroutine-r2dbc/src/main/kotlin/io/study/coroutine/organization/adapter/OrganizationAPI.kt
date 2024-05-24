package io.study.coroutine.organization.adapter

import io.micronaut.http.annotation.*
import io.study.coroutine.organization.application.port.OrganizationService
import io.study.coroutine.organization.domain.Organization

@Controller("/organizations")
class OrganizationAPI(private val organizationService: OrganizationService) {

    @Get("/{id}")
    suspend fun getOrganization(@PathVariable id: Long) = organizationService.getOrganization(id)

    @Post
    suspend fun addOrganization(@Body organizationRequest: OrganizationRequest) =
        organizationService.addOrganization(organizationRequest.toOrganization())

    @Put("/{id}")
    suspend fun updateOrganization(
        @PathVariable id: Long,
        @Body organizationRequest: OrganizationRequest
    ): Organization {
        return organizationService.updateOrganization(id, organizationRequest.toOrganization())
    }

    @Delete("/{id}")
    suspend fun deleteOrganization(@PathVariable id: Long) = organizationService.deleteOrganization(id)


    @Post("/{id}/add-users-to-project")
    suspend fun addUsersToProject(@PathVariable id: Long, @Body addUsersRequest: AddUsersRequest): Map<String, Int> {
        return organizationService.addUsersToProject(id, addUsersRequest)
    }

    // 조직에 속해있는 사용자 목록
    @Get("/{id}/users")
    suspend fun getOrganizationUsers(@PathVariable id: Long) = organizationService.getOrganizationUsers(id)
}