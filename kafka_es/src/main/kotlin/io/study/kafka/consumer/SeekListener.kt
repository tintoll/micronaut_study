package io.study.kafka.consumer

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.OffsetStrategy
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.serde.ObjectMapper
import io.study.kafka.models.Event
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory
import java.time.Duration

@KafkaListener(
    batch = true,
    offsetReset = OffsetReset.EARLIEST, groupId = "\${topic.consumer}-group",
    offsetStrategy = OffsetStrategy.DISABLED
)
class SeekListener(private val objectMapper: ObjectMapper) {

    @Topic("\${topic.consumer}")
    fun receive(
        records: List<ConsumerRecord<String, String>>,
        kafkaConsumer: Consumer<String, String>
    ) {

        val currentOffsetMap = mutableMapOf<TopicPartition, OffsetAndMetadata>()
        val copiedEvents = mutableSetOf<Event>()
        val result = records.map { record ->
            val key = record.key()
            val value = record.value()
            val offset = record.offset()
            val partition = record.partition()
            val timestamp = record.timestamp()
            val event = objectMapper.readValue(value, Event::class.java)
            LOG.info("Received Product: $key - $value - $offset - $partition - $timestamp")

            event.eid = 1

            if (event.eventTime == 1714458278000) {
                val e = seekWithRangeOffset(kafkaConsumer, 5L, 8L, 0)
                println(e)
                copiedEvents.addAll(e)
            }

            currentOffsetMap[TopicPartition("\${topic.consumer}", partition)] = OffsetAndMetadata(offset + 1)
            event
        }.toList()

        println(result)
        println("=========")
        println(copiedEvents)


    }

    private fun seekWithRangeOffset(
        kafkaConsumer: Consumer<String, String>,
        start: Long,
        end: Long,
        partition: Int
    ): List<Event> {
        val partitions = kafkaConsumer.assignment()
        return partitions.asSequence().filter { it.partition() == partition }.map {
            kafkaConsumer.seek(it, start)
            val records = kafkaConsumer.poll(Duration.ofMillis(1000))

            records.filter { it.offset() < end }.map {
                val event = objectMapper.readValue(it.value(), Event::class.java)
                event.eid = 2
                event
            }
        }.flatten().toList()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(ProductListener::class.java)
    }
}