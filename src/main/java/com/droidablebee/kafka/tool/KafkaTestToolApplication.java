package com.droidablebee.kafka.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
public class KafkaTestToolApplication {

    static final String RANDOM_GROUP_ID = "--random-group-id";

    public static void main(String[] args) {

        System.setProperty("app.kafka.randomGroupIdSuffix", generateRandomGroupIdSuffix(args));
        SpringApplication.run(KafkaTestToolApplication.class, args);
    }

    protected static String generateRandomGroupIdSuffix(String[] args) {

        String randomGroupIdSuffix = "";

        if(Arrays.asList(args).contains(RANDOM_GROUP_ID)) {
            randomGroupIdSuffix = "-" + UUID.randomUUID();
        }

        return randomGroupIdSuffix;
    }

}
