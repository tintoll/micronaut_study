package io.study.coroutine.event

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import jakarta.validation.Valid

@Controller("/events")
class EventAPI(private val eventService: EventService) {

    @Get
    suspend fun getEvents() = eventService.getEvents()

    @Get("/{id}")
    suspend fun getEventById(@PathVariable id: Long) = eventService.getEventById(id)

    @Post
    @Status(value = HttpStatus.CREATED)
    suspend fun createEvent(@Valid @Body eventRequest: EventRequest) : Event  {
        // validation 처리 : 정리돼서 리턴해주는 부분을 만드는 방법은?
        // name은 중복체크 해야함.

        return eventService.createEvent(eventRequest.toEvent())
    }

    @Put("/{id}")
    suspend fun updateEvent(@PathVariable id: Long, @Body eventRequest: EventRequest)  : Event {
        // validation 처리

        eventRequest.id = id
        return eventService.updateEvent(eventRequest.toEvent())
    }

    @Delete("/{id}")
    @Status(value = HttpStatus.NO_CONTENT)
    suspend fun deleteEvent(id: Long) {
        eventService.deleteEvent(id)
    }

    @Post("/import/csv")
    suspend fun importCsv(@Body csv: String) {
        println("import csv")
    }
    @Post("/export/csv")
    suspend fun exportCsv() {
        println("export csv")
    }

    @Post("/duplicate/{id}")
    suspend fun duplicateEvent(@PathVariable id: Long) {
        eventService.duplicateEvent(id)
    }

}