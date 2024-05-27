package io.study.coroutine.event

import io.study.coroutine.common.EventNotFoundException
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList

@Singleton
class EventService(private val eventRepository: EventRepository) {

    suspend fun getEvents(): List<Event> {
        return eventRepository.findAll().toList()
    }

    suspend fun getEventById(id: Long): Event {
        return eventRepository.findById(id) ?: throw EventNotFoundException("존재하지 않는 이벤트입니다. id: $id")
    }

    suspend fun createEvent(toEvent: Event): Event {
        // 존재하는 프로젝트인지 체크

        return eventRepository.save(toEvent)
    }

    suspend fun updateEvent(toEvent: Event): Event {
        // 존재하지 않은 이벤트 인지 체크

        return eventRepository.update(toEvent)
    }

    suspend fun deleteEvent(id: Long) {
        eventRepository.deleteById(id)
    }

    suspend fun duplicateEvent(id: Long) {

    }


}