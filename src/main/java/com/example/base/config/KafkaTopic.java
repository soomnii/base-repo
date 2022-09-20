package com.example.base.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopic {
    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    String bootstrapServers;

    @Value(value = "${kafka.topic.sample}")
    String sampleTopic;

    @Value(value = "${kafka.topic.object-sample}")
    String sampleObjectTopic;

    @Bean
    public NewTopic sampleTopic() {
        return new NewTopic(sampleTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic sampleObjectTopic() {
        return new NewTopic(sampleObjectTopic, 1, (short) 1);
    }
}
