package io.study.kafka.controller

import co.elastic.clients.elasticsearch.ElasticsearchClient
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.study.kafka.models.Event
import io.study.kafka.producer.EventSender
import io.study.kafka.producer.ProductClient


@Controller
class KafkaTestController(val productClient: ProductClient,
                          private val eventSender: EventSender,
        private val esClient: ElasticsearchClient
) {

    @Get("/kafka/send-product")
    fun sendProduct() {

        val events = listOf(
            Event(1, "page_view", 1712642054000, "D1",null, 1),
            Event(1, "page_view", 1712642055000, "D1",null, 1),
            Event(1, "page_view", 1712642070000, "D1", "U1", 1),
            Event(1, "page_view", 1712642071000, "D1", "U1", 1),
            Event(1, "page_view", 1712642072000, "D1", "U1", 1),
            Event(1, "page_view", 1712642080000, "D1",null, 1),
            Event(1, "page_view", 1714456958000, "D1",null, 1),
            Event(1, "page_view", 1714457438000, "D1",null, 1),
            Event(1, "page_view", 1714458278000, "D1", "U2", 2),
            Event(1, "page_view", 1714458279000, "D1",null, 2),
            Event(1, "page_view", 1714458280000, "D1",null, 2),
            Event(1, "page_view", 1714458281000, "D1",null, 2),
            Event(1, "page_view", 1714458282000, "D1",null, 2),
            Event(1, "page_view", 1714458283000, "D1","U3",3),
        )
//        for (event in events) {
//            productClient.sendProduct(event.eid!!, event)
//        }

        eventSender.sendEvents(events)
    }

    @Get("/es/info")
    fun esInfo() : String  {
        return esClient.info().toString()
    }
}