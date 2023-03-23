package com.droidablebee.kafka.tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.LoggingErrorHandler;

@Configuration
public class KafkaConfiguration {

  @Bean
  public LoggingErrorHandler errorHandler() {

        return new LoggingErrorHandler();
    }
}