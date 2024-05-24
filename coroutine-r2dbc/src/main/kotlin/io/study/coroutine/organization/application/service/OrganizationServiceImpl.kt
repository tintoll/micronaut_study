package io.study.coroutine.organization.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.study.coroutine.organization.adapter.AddUsersRequest
import io.study.coroutine.organization.application.port.OrganizationRepository
import io.study.coroutine.organization.application.port.OrganizationService
import io.study.coroutine.organization.domain.Organization
import jakarta.inject.Inject
import jakarta.inject.Singleton
import jakarta.transaction.Transactional
import kotlinx.coroutines.reactive.awaitSingle
import org.reactivestreams.Publisher
import java.net.URL


@Singleton
open class OrganizationServiceImpl(private val organizationRepository: OrganizationRepository) : OrganizationService {

    @Client("http://localhost:8200")
    @Inject
    lateinit var httpClient: HttpClient

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

    override suspend fun addUsersToProject(id: Long, addUsersRequest: AddUsersRequest): Map<String, Int> {
        val uri = "http://localhost:8200/projects/${addUsersRequest.projectId}/add-users"

        val req = HttpRequest.POST(uri, mapOf("users" to addUsersRequest.users))
            .header("Content-Type", "application/json")

        val result = httpClient.retrieve(req, Argument.mapOf(String::class.java, Int::class.java))
        val awaitSingle: MutableMap<String, Int> = result.awaitSingle()
        logger.info {" result : $awaitSingle" }

        return awaitSingle
    }


    companion object {
        private val logger = KotlinLogging.logger {  }
    }
}