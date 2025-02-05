package io.study.kafka.producer

import io.micronaut.context.ApplicationContext
import io.micronaut.core.util.StringUtils
import io.study.kafka.models.Event
import org.junit.jupiter.api.Test

class EventSenderTest {

//    @Test
//    fun testSendEvents() {
//        ApplicationContext.run(mapOf("kafka.enabled" to StringUtils.TRUE, "spec.name" to "EventSenderTest"))
//            .use { ctx ->
//                val eventSender = ctx.getBean(EventSender::class.java)
//                eventSender.sendEvents(listOf(Event(1, "page_view", 1712642054000, "D1", null, 1)))
//            }
//    }
}