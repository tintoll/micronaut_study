package io.study.kafka.consumer

import io.micronaut.configuration.kafka.annotation.*
import io.micronaut.context.annotation.Property
import io.micronaut.messaging.Acknowledgement
import io.micronaut.serde.ObjectMapper
import io.study.kafka.models.Event
import io.study.kafka.producer.ProductClient
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.IsolationLevel
import org.slf4j.LoggerFactory

//@KafkaListener(
//    offsetReset = OffsetReset.EARLIEST,
//    groupId = "\${topic.consumer}-group",
//    offsetStrategy = OffsetStrategy.DISABLED,
//    properties = [
//        Property(name = ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, value = "\${org-id}-group-instance-\${instance-id}"),
//        Property(name = ConsumerConfig.MAX_POLL_RECORDS_CONFIG, value = "10"),
//    ],
//    //batch = true,

//    producerTransactionalId = "tx-event-\${topic.consumer}-\${random.uuid}",
//    offsetStrategy = OffsetStrategy.SEND_TO_TRANSACTION,
//    isolation = IsolationLevel.READ_COMMITTED,

//    // 에러 전략
//    errorStrategy = ErrorStrategy(
//        value = ErrorStrategyValue.RETRY_ON_ERROR,
//        retryDelay = "50ms",
//        retryCount = 3
//    )
//)
class ProductListener(private val objectMapper: ObjectMapper,
                      @Property(name = "topic.consumer") val topic: String,
                      private val productClient: ProductClient
    ) {
    private var isSeek = false

//    @Topic("\${topic.consumer}")
//    @SendTo("\${topic.producer}")

//    fun batchReceive(
//        records: List<ConsumerRecord<String, String>>,
//        kafkaConsumer: Consumer<String, String>
//    ) {
//        println("records size : ${records.size}")
//
//        val currentOffsetMap = mutableMapOf<TopicPartition, OffsetAndMetadata>()
//        val result = records.map { record ->
//            val key = record.key()
//            val value = record.value()
//            val offset = record.offset()
//            val partition = record.partition()
//            val timestamp = record.timestamp()
//            val event = objectMapper.readValue(value, Event::class.java)
//            LOG.info("Received Product: $key - $value - $offset - $partition - $timestamp")
//
//            event.eid = 1
//            currentOffsetMap[TopicPartition(topic,partition)] = OffsetAndMetadata(offset + 1)
//            event
//        }.toList()
//        println(result)
//
//        eventSender.sendEvents(result)
//
////        return result
//
//        // commit
//        kafkaConsumer.commitSync(currentOffsetMap)
//
//    }




    @Topic("\${topic.consumer}")
    fun receive(
        @KafkaKey deviceId: String,
        value: String,
        offset: Long,
        partition: Int,
        timestamp: Long,
        acknowledgement: Acknowledgement,
//        ops: KafkaSeekOperations
    ) {
        LOG.info("Received Product: $deviceId - $value - $offset - $partition - $timestamp")
        val event = objectMapper.readValue(value, Event::class.java)
        event.eid = 1

//        if (event.eventTime == 1714458278000 && !isSeek) {
//            ops.defer(KafkaSeekOperation.seek(TopicPartition(topic, partition), 5))
//            isSeek = true
//        }
//
//        if (isSeek && offset >= 5L) {
//            if (offset == 7L) {
//                //isSeek = false
//            }
//        }

        productClient.sendProduct(event.eid!!, event)
//         commit
        acknowledgement.ack()
    }


//    @Topic("\${topic.consumer}")
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