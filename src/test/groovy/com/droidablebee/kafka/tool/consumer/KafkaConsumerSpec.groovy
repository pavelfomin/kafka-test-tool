package com.droidablebee.kafka.tool.consumer

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.messaging.MessageHeaders
import spock.lang.Specification

class KafkaConsumerSpec extends Specification {

    KafkaConsumer kafkaConsumer = Spy(new KafkaConsumer())

    ConsumerRecord<String, GenericRecord> consumerRecord = Mock()
    GenericRecord message = Mock()
    MessageHeaders headers = Mock()
    String key = "key"

    def "consume"() {

        when:
        kafkaConsumer.consume(consumerRecord, headers)

        then:
        consumerRecord.key() >> key
        consumerRecord.value() >> message

        1 * kafkaConsumer.logMessage(consumerRecord, headers)
    }

}
