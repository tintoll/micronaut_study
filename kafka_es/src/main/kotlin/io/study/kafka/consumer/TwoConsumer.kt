package io.study.kafka.consumer

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.OffsetStrategy
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Property
import io.micronaut.serde.ObjectMapper
import io.study.kafka.models.Event
import jakarta.inject.Singleton
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration

@Singleton
class TwoConsumer(private val objectMapper: ObjectMapper,
    @Property(name = "topic.consumer") val topicName: String,

) {

    @KafkaListener(
        batch = true,
        offsetReset = OffsetReset.EARLIEST, groupId = "\${topic.consumer}-two-consumer-group",
        offsetStrategy = OffsetStrategy.DISABLED
    )
    @Topic("\${topic.consumer}")
    fun baseConsume(records: List<ConsumerRecord<String, String>>,
                    kafkaConsumer: Consumer<String, String>) {
        println("records: ${records.size}")
        val events = records.map { record ->
            val key = record.key()
            val value = record.value()
            val offset = record.offset()
            val event = objectMapper.readValue(value, Event::class.java)

            val c = copyConsume(record.partition(), offset, offset + 1)
            println("c: $c")
            event.eid = 1
            event
        }.toList()
        println("events: $events")
    }



    fun copyConsume(partition:Int, startOffset:Long, endOffset:Long) : List<Event> {
        val consumer = KafkaConsumer(
            mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9091,localhost:9092,localhost:9093",
            ),
            StringDeserializer(),
            StringDeserializer()
        )
        val topicPartition = TopicPartition(topicName, partition)
        consumer.assign(listOf(topicPartition))

        // 시작 Offset 설정 (10)
        consumer.seek(topicPartition, startOffset)
        println("startOffset : $startOffset, endOffset : $endOffset")
        val records = consumer.poll(Duration.ofMillis(10000))

        val copyEvents = records.map { record ->
            val key = record.key()
            val value = record.value()
            val offset = record.offset()
            val event = objectMapper.readValue(value, Event::class.java)
            event.eid = 1
            event
        }.toList()

        consumer.close()
        return copyEvents
    }



}