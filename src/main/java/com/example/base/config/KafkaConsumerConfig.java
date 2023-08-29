package com.example.base.config;

import com.example.base.model.topic.KafkaPubVO;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    @Value(value = "${spring.kafka.consumer.group-id}")
    String groupId;

    @Autowired KafkaTemplate<String, String> stringTemplate;

    @Autowired KafkaTemplate<String, KafkaPubVO> jsonTemplate;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO>
            jsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getJsonConsumerFactory(getJsonConsumerFactoryConfig()));
        factory.setReplyTemplate(jsonTemplate);
//        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
            stringListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getStringConsumerFactory(getStringConsumerFactoryConfig()));
//        factory.setContainerProperr
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
//        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, );
                consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
                return consumerConfig;
    }

    @Bean
    public KafkaListenerErrorHandler kafkaErrorHandler() {
        return (message, exception ) -> {
            log.error("[KafkaErrorHandler] kafkaMessage=[" + message.getPayload() + "], errorMessage=[" + exception.getMessage() + "]");

            //ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) m.getPayload();
            // 메시지를 더 가공하거나 별도 처리를 하고..

            return message.getPayload();  // sendTo("토픽명")에 입력된 토픽으로 전달 될 메시지 내용
        };
    }

//    @Bean
//    public DefaultErrorHandler errorHandler() {
//        BackOff fixedBackOff = new FixedBackOff(5000, 3);
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
//            // logic to execute when all the retry attemps are exhausted
//        }, fixedBackOff);
//        return errorHandler;
//    }
}
