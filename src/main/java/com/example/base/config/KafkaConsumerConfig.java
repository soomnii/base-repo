package com.example.base.config;

import com.example.base.model.topic.KafkaPubVO;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    String bootstrapServers;

    @Value(value = "${spring.kafka.consumer.group-id}")
    String groupId;

    @Autowired KafkaTemplate<String, String> stringTemplate;

    @Autowired KafkaTemplate<String, ?> jsonTemplate;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO>
            jsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getJsonConsumerFactory(getJsonConsumerFactoryConfig()));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
            stringListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getStringConsumerFactory(getStringConsumerFactoryConfig()));
        return factory;
    }

    public DefaultKafkaConsumerFactory<String, KafkaPubVO> getJsonConsumerFactory(
            Map<String, Object> config) {
        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(KafkaPubVO.class)));
    }

    public DefaultKafkaConsumerFactory<String, String> getStringConsumerFactory(
            Map<String, Object> config) {
        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new StringDeserializer()));
    }

    public Map<String, Object> getJsonConsumerFactoryConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return consumerConfig;
    }

    public Map<String, Object> getStringConsumerFactoryConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return consumerConfig;
    }
}
