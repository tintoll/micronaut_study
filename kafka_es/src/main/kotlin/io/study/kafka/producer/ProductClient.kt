package io.study.kafka.producer

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.study.kafka.models.Event

@KafkaClient(batch = true)
interface ProductClient {

    // method returns void this means the method will send the ProducerRecord and block until the response is received.
    @Topic("\${topic.name}.test")
    fun sendProduct(@KafkaKey key: Long, event: Event)
}