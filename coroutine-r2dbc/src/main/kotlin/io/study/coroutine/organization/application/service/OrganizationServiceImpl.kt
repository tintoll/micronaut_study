package io.study.coroutine.organization.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.study.coroutine.organization.adapter.AddUsersRequest
import io.study.coroutine.organization.application.port.OrganizationRepository
import io.study.coroutine.organization.application.port.OrganizationService
import io.study.coroutine.organization.domain.Organization
import io.study.coroutine.user.User
import io.study.coroutine.user.UserService
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import kotlinx.coroutines.reactive.awaitSingle


@Singleton
open class OrganizationServiceImpl(
    private val organizationRepository: OrganizationRepository,
    private val userService: UserService,
    private val httpClient: HttpClient
) : OrganizationService {

    override suspend fun getOrganization(id: Long): Organization? {
        return organizationRepository.findById(id)
    }

    @Transactional
    override suspend fun addOrganization(organization: Organization): Organization {
        return organizationRepository.save(organization)
    }

    @Transactional
    override suspend fun updateOrganization(id: Long, organization: Organization): Organization {
        organizationRepository.update(id, organization.name, organization.description)
        return getOrganization(id)!!
    }

    @Transactional
    override suspend fun deleteOrganization(id: Long) {
        organizationRepository.deleteById(id)
    }

    @Transactional
    override suspend fun addUsersToProject(id: Long, addUsersRequest: AddUsersRequest): Map<String, Int> {
        val uri = "http://localhost:8200/projects/${addUsersRequest.projectId}/add-users"

        val req = HttpRequest.POST(uri, mapOf("users" to addUsersRequest.users))
            .header("Content-Type", "application/json")

        val result = httpClient.retrieve(req, Argument.mapOf(String::class.java, Int::class.java))
        val awaitSingle: MutableMap<String, Int> = result.awaitSingle()
        logger.info {" result : $awaitSingle" }

        return awaitSingle
    }

    override suspend fun getOrganizationUsers(id: Long): List<User> {
        return userService.getUsersByOrgId(id)
    }

    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}