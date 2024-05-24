package io.study.coroutine.user

import jakarta.inject.Named
import jakarta.inject.Singleton


@Singleton
@Named("SignalUserService")
open class UserService(private val userRepository: UserRepository) {
    suspend fun getUsersByOrgId(orgId: Long) = userRepository.findAllByOrgId(orgId)

    suspend fun getUsersByIds(ids: List<Long>) = userRepository.findByIdIn(ids)

}