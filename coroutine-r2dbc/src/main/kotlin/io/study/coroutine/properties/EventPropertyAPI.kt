package io.study.coroutine.properties

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/event-properties")
class EventPropertyAPI(private val eventPropertyService: EventPropertyService) {

    @Get
    suspend fun getEventProperties() = eventPropertyService.getEventProperties()

}