package io.study.kafka.consumer

import io.micronaut.configuration.kafka.ConsumerSeekAware
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.seek.KafkaSeekOperation
import io.micronaut.configuration.kafka.seek.KafkaSeeker
import io.micronaut.context.annotation.Requires
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition


//@KafkaListener
@Requires(property = "spec.name", value = "EventListenerTest")
class LogListener : ConsumerRebalanceListener, ConsumerSeekAware  {

    // ConsumerSeekAware
    override fun onPartitionsAssigned(partitions: MutableCollection<TopicPartition>, seeker: KafkaSeeker) {
        //  오프셋을 읽고 특정 위치로 찾는 데 사용할 수 있습니다. 이 간단한 예제에서는 오프셋 1을 찾습니다(첫 번째 레코드는 건너뛰기)
        partitions.stream().map { tp -> KafkaSeekOperation.seek(tp, 1) }.forEach {seeker::perform}
    }


    // ConsumerRebalanceListener
    override fun onPartitionsRevoked(partitions: MutableCollection<TopicPartition>?) {
        // save offsets here
    }
    override fun onPartitionsAssigned(partitions: MutableCollection<TopicPartition>?) {
        TODO("Not yet implemented")
    }

    override fun onPartitionsLost(partitions: MutableCollection<TopicPartition>?) {
        TODO("Not yet implemented")
    }

}