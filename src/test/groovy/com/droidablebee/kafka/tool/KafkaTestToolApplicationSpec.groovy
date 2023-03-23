package com.droidablebee.kafka.tool


import spock.lang.Specification
import spock.lang.Unroll

import static KafkaTestToolApplication.RANDOM_GROUP_ID

class KafkaTestToolApplicationSpec extends Specification {

    @Unroll
    def "generate random group id suffix"() {

        when:
        String suffix = KafkaTestToolApplication.generateRandomGroupIdSuffix(args as String[])

        then:
        suffix =~ expected

        where:
        args                 | expected
        []                   | ""
        ["anything", "else"] | ""
        [RANDOM_GROUP_ID]    | '^-[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$'
    }

}
