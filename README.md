# Kafka Test Tool
Kafka Test Tool includes a kafka topic consumer which outputs the received message in JSON format.

## Running kafka test tool
To consume AVRO messages from `my-avro-topic` topic in a shared environment use:
```
java -jar ist-kafka-test-tool-version.jar --app.kafka.topic=my-avro-topic \
--spring.kafka.bootstrap-servers=kafka.ackme.com:9092 \
--spring.kafka.properties.schema.registry.url=http://schema.ackme.com:8081
```

To consume JSON messages from `my-json-topic` topic in a shared environment use:
```
java -jar ist-kafka-test-tool-version.jar --app.kafka.topic=my-json-topic \
--spring.kafka.bootstrap-servers=kafka.ackme.com:9092 \
--spring.kafka.properties.schema.registry.url=http://schema.ackme.com:8081 \
--spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```

### Available arguments
 * app.kafka.topic=(topic name to consume)
 * random-group-id (random group id generation flag, if specified `app.kafka.randomGroupIdSuffix` is set to a random UUID)
 * spring.kafka.bootstrap-servers=(kafka bootstrap servers) [localhost:9092]
 * spring.kafka.properties.schema.registry.url=(schema registry url) [http://localhost:8081]
 * spring.kafka.consumer.group-id=(group id) [${spring.application.name}-${user.name}${app.kafka.randomGroupIdSuffix}]
 * spring.kafka.consumer.auto-offset-reset=(offset reset) [earliest] See [docs](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html#auto.offset.reset)
 * spring.kafka.consumer.value-deserializer=(consumer deserializer) [io.confluent.kafka.serializers.KafkaAvroDeserializer]

### Kafka Consumer Group Id
Each Kafka consumer must specify a [group.id](https://kafka.apache.org/documentation/#consumerconfigs_group.id) property. 
The consumer `group id` controls the topic offset. 
If the same `group id` is used for a subsequent invocation of the Kafka consumer then the topic offset 
will point to the last message consumed previously (will consume and output newly received messages only).
To consume the messages from the beginning again, the `group id` needs to change. Either `--random-group-id` parameter
can be used to automatically add `randomGroupIdSuffix` to the `group id` or `spring.kafka.consumer.group-id=(group id)`
can be used to specify the `group id` property explicitly.

The default value for the `group id` property is user specific `${application.name}-${user.name}${app.kafka.randomGroupIdSuffix}`.

### Kafka Consumer Deserializer
By default, `KafkaAvroDeserializer` is used by the Kafka consumer. It can be replaced with `org.apache.kafka.common.serialization.StringDeserializer`
to consume json (or avro messages w/out serializing them from avro binary format)

### Prerequisites
Requires JVM 11 installed locally:
```
$ java -version
```
Expected output similar to:
```
java version "11.0.10" 2021-01-19 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.10+8-LTS-162)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.10+8-LTS-162, mixed mode)
```

## Running tests
`./gradlew clean test`

## Publish test event
```shell
docker exec -it broker bash
kafka_topic=event
kafka_url=localhost:9092
kafka-console-producer --bootstrap-server $kafka_url --topic $kafka_topic --property "parse.key=true" --property "key.separator=:"
```
