package io.study.kafka.producer

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.study.kafka.models.Event
import jakarta.inject.Singleton
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord


@Requires(property = "spec.name", value = "EventSenderTest")
@Singleton
class EventSender(@param:KafkaClient private val kafkaProducer: Producer<Long, Event>,
                  @Property(name = "topic.producer") val topicName:String,
    ) {

    fun sendEvents(events: List<Event>) {
        events.forEach {
            kafkaProducer.send(ProducerRecord(topicName, it.eid!!, it))
        }
    }
}