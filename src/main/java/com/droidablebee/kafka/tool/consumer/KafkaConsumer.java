package com.droidablebee.kafka.tool.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic}")
    public void consume(ConsumerRecord<String, Object> consumerRecord, MessageHeaders headers, Acknowledgment acknowledgment) {

        logMessage(consumerRecord, headers);
        acknowledgment.acknowledge();
    }

    protected void logMessage(ConsumerRecord<String, Object> consumerRecord, MessageHeaders headers) {

        logger.debug("Received message: key: {} value: {} headers: {}", consumerRecord.key(), consumerRecord.value(), headers);
    }

}
