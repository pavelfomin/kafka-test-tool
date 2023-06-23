package com.droidablebee.kafka.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.LoggingErrorHandler;

@Configuration
public class KafkaConfiguration {

    /**
     * Default error handler for Kafka consumers unless `@RetryableTopic` is used.
     */
    @Bean
    public CommonErrorHandler errorHandler() {

        return new CommonLoggingErrorHandler();
    }
}