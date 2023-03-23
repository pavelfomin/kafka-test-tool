package com.droidablebee.kafka.tool.config

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.*

@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
class TestKafkaConfig {

    private final KafkaProperties kafkaProperties

    TestKafkaConfig(KafkaProperties kafkaProperties) {

        this.kafkaProperties = kafkaProperties
    }

    @Bean
    SchemaRegistryClient schemaRegistryClient() {

        new MockSchemaRegistryClient()
    }

    @Bean
    KafkaAvroSerializer kafkaAvroSerializer() {

        new KafkaAvroSerializer(schemaRegistryClient(), kafkaProperties.buildProducerProperties())
    }

    @Bean
    KafkaAvroDeserializer kafkaAvroDeserializer() {

        new KafkaAvroDeserializer(schemaRegistryClient(), kafkaProperties.buildConsumerProperties())
    }

    @Bean
    ProducerFactory istProducerFactory() {

        new DefaultKafkaProducerFactory(
                kafkaProperties.buildProducerProperties(),
                new StringSerializer(),
                kafkaAvroSerializer()
        )
    }

    @Bean
    ConsumerFactory istConsumerFactory() {

        new DefaultKafkaConsumerFactory(
                kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                kafkaAvroDeserializer()
        )
    }

}