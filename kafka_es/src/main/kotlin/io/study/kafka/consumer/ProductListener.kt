package io.study.kafka.consumer

import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Property
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.slf4j.LoggerFactory

@KafkaListener(
    offsetReset = OffsetReset.EARLIEST,
    properties = [Property(name = ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, value = "false")]
)
class ProductListener {

    @Topic("\${topic.name}")
    fun receive(@KafkaKey key: String, event: String) {
        LOG.info("Received Product: $key - $event")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(ProductListener::class.java)
    }
}