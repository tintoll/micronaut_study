package io.study.coroutine.organization.adapter

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.study.coroutine.organization.application.port.OrganizationService

@Controller("/organizations")
class OrganizationAPI(private val organizationService: OrganizationService) {

    @Get("/{id}")
    suspend fun getOrganization(@PathVariable id: Long) = organizationService.getOrganization(id)

    @Post
    suspend fun addOrganization(@Body organizationRequest: OrganizationRequest) =
        organizationService.addOrganization(organizationRequest.toOrganization())

    @Put("/{id}")
    suspend fun updateOrganization(@PathVariable id: Long, @Body organizationRequest: OrganizationRequest) =
        organizationService.updateOrganization(id, organizationRequest.toOrganization())

    @Delete("/{id}")
    suspend fun deleteOrganization(@PathVariable id: Long) = organizationService.deleteOrganization(id)
}