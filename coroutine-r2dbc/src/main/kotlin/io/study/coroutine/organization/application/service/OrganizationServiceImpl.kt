package io.study.coroutine.organization.application.service

import io.study.coroutine.organization.application.port.OrganizationRepository
import io.study.coroutine.organization.application.port.OrganizationService
import io.study.coroutine.organization.domain.Organization
import jakarta.inject.Singleton
import jakarta.transaction.Transactional

@Singleton
open class OrganizationServiceImpl(private val organizationRepository: OrganizationRepository) : OrganizationService {

    override suspend fun getOrganization(id: Long): Organization? {
        return organizationRepository.findById(id)
    }

    @Transactional
    override suspend fun addOrganization(organization: Organization): Organization {
        return organizationRepository.save(organization)
    }

    @Transactional
    override suspend fun updateOrganization(id: Long, organization: Organization) {
        organizationRepository.update(id, organization.name, organization.description)
    }

    @Transactional
    override suspend fun deleteOrganization(id: Long) {
        organizationRepository.deleteById(id)
    }
}