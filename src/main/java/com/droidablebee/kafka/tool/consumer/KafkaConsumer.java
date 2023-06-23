package com.droidablebee.kafka.tool.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.RetryTopicHeaders;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic}")
    @RetryableTopic(attempts = "4")
    public void consume(ConsumerRecord<String, Object> consumerRecord, MessageHeaders headers) {

        logMessage(consumerRecord, headers);
        process(consumerRecord, headers);
    }

    private void process(ConsumerRecord<String, ?> consumerRecord, MessageHeaders headers) {
        String value = (String) consumerRecord.value();
        if (value.startsWith("bad")) {
            throw new RuntimeException(
                    "Test processing failure for key: " + consumerRecord.key() + " value:" + consumerRecord.value());
        }
    }

    protected void logMessage(ConsumerRecord<String, ?> consumerRecord, MessageHeaders headers) {

        byte[] attempts = (byte[]) headers.get(RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS);
        byte[] errorMessage = (byte[]) headers.get(KafkaHeaders.EXCEPTION_MESSAGE);
        String topic = (String) headers.get(KafkaHeaders.RECEIVED_TOPIC);

        logger.debug("Received message from topic: '{}', attempts: {}, key: '{}', value: '{}', previous error: '{}', headers: {}, ",
                topic,
                attempts == null ? 1 : ByteBuffer.wrap(attempts).getInt(),
                consumerRecord.key(),
                consumerRecord.value(),
                errorMessage == null ? "" : new String(errorMessage),
                headers
        );
    }

    protected void logError(ConsumerRecord<String, ?> consumerRecord, MessageHeaders headers) {

        byte[] attempts = (byte[]) headers.get(RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS);
        String topic = (String) headers.get(KafkaHeaders.RECEIVED_TOPIC);
        byte[] stackTrace = (byte[]) headers.get(KafkaHeaders.EXCEPTION_STACKTRACE);

        logger.error("Failed to process message from topic: '{}', attempts: {}, key: '{}', value: '{}', headers: {}, error: {}",
                topic,
                attempts == null ? 1 : ByteBuffer.wrap(attempts).getInt(),
                consumerRecord.key(),
                consumerRecord.value(),
                stackTrace == null ? "" : new String(stackTrace)
        );
    }

    @DltHandler
    public void processDLT(ConsumerRecord<String, Object> consumerRecord, MessageHeaders headers) {
        logError(consumerRecord, headers);
    }

}
