package io.study.kafka.consumer

import io.micronaut.configuration.kafka.annotation.*
import io.micronaut.configuration.kafka.seek.KafkaSeekOperation
import io.micronaut.configuration.kafka.seek.KafkaSeekOperations
import io.micronaut.context.annotation.Property
import io.micronaut.messaging.Acknowledgement
import io.micronaut.serde.ObjectMapper
import io.study.kafka.models.Event
import jdk.incubator.vector.VectorOperators.LOG
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory

@KafkaListener(
    offsetReset = OffsetReset.EARLIEST,
    groupId = "\${topic.consumer}-group",
    offsetStrategy = OffsetStrategy.DISABLED,
    properties = [
        Property(name = ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, value = "\${org-id}-group-instance-\${instance-id}"),
    ]
)
class ProductListener(private val objectMapper: ObjectMapper, @Property(name = "topic.consumer") val topic: String) {
    private var isSeek = false

    @Topic("\${topic.consumer}")
    fun receive(
        @KafkaKey deviceId: String,
        value: String,
        offset: Long,
        partition: Int,
        timestamp: Long,
//        acknowledgement: Acknowledgement,
        ops: KafkaSeekOperations
    ) {
        LOG.info("Received Product: $deviceId - $value - $offset - $partition - $timestamp")
        val event = objectMapper.readValue(value, Event::class.java)

        if (event.eventTime == 1714458278000 && !isSeek) {
            ops.defer(KafkaSeekOperation.seek(TopicPartition(topic, partition), 5))
            isSeek = true
        }

        if (isSeek && offset >= 5L) {
            if (offset == 7L) {
                //isSeek = false
            }
        }

        // commit
        //acknowledgement.ack()
    }


//    @Topic("\${topic.name}")
//    fun receive(record: ConsumerRecord<String, String>, kafkaConsumer: Consumer<String, String>) {
//        val key = record.key()
//        val value = record.value()
//        val offset = record.offset()
//        val partition = record.partition()
//        val timestamp = record.timestamp()
//        LOG.info("Received Product: $key - $value - $offset - $partition - $timestamp")
//
//        // commit
//        kafkaConsumer.commitSync(
//            Collections.singletonMap(
//                TopicPartition(record.topic(), record.partition()),
//                OffsetAndMetadata(record.offset() + 1)
//            )
//        )
//    }

    companion object {
        private val LOG = LoggerFactory.getLogger(ProductListener::class.java)
    }
}