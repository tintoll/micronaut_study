package io.study.coroutine.properties

import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList

@Singleton
class EventPropertyService(private val eventPropertyRepository: EventPropertyRepository) {

    suspend fun getEventProperties(): List<EventProperty>  = eventPropertyRepository.findAll().toList()




}