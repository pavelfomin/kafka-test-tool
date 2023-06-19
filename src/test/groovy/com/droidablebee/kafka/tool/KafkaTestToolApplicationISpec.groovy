package com.droidablebee.kafka.tool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.kafka.test.context.EmbeddedKafka
import spock.lang.Specification

@SpringBootTest
@EmbeddedKafka(bootstrapServersProperty = "spring.kafka.bootstrap-servers")
class KafkaTestToolApplicationISpec extends Specification {

	@Autowired
	Environment environment

	def "context loads successfully"() {

		expect:
		environment
	}

}
