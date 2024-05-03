package io.study.kafka.producer

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import io.study.kafka.models.Event
import jakarta.inject.Singleton
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.KafkaException


//@Requires(property = "spec.name", value = "EventSenderTest")
@Singleton
class EventSender(@param:KafkaClient(id = "test-client", transactionalId = "qq-tx-id-\${random.uuid}") private val kafkaProducer: Producer<Long, Event>,
                  @Property(name = "topic.producer") val topicName:String,
    ) {

    fun sendEvents(events: List<Event>) {
        kafkaProducer.initTransactions()

        kafkaProducer.beginTransaction()
        try {
            events.forEach {
                kafkaProducer.send(ProducerRecord(topicName, it.eid!!, it))
            }
            kafkaProducer.commitTransaction()
        } catch (e: KafkaException) {
            kafkaProducer.abortTransaction()
            throw e
        }

    }
}