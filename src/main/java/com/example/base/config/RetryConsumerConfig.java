package com.example.base.config;

import com.example.base.model.topic.KafkaPubVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class RetryConsumerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    @Value(value = "${spring.kafka.consumer.group-id}")
    String groupId;


    @Autowired KafkaTemplate<String, KafkaPubVO> jsonTemplate;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO>
            retryJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getJsonConsumerFactory(getJsonConsumerFactoryConfig()));
        factory.setReplyTemplate(jsonTemplate);
//        factory.getContainerProperties()
//                .setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setCommonErrorHandler(errorHandler()); // retry case1 - backoff 설정
        return factory;
    }

    public DefaultKafkaConsumerFactory<String, KafkaPubVO> getJsonConsumerFactory(
            Map<String, Object> config) {
        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(KafkaPubVO.class)));
    }

    public Map<String, Object> getJsonConsumerFactoryConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, );
        //consumerConfig.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, true);
//        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        consumerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return consumerConfig;
    }




    /**
     * retry case1. backoff설정을 통한 retry
     * factory.setCommonErrorHandler(errorHandler());
     * errorHandler에 FixedBackOff()설정하여 재시도 자동 실행하는 case
     *
     */
    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(20000, 3);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            // logic to execute when all the retry attemps are exhausted
            log.debug("backoff done --- final action");
        }, fixedBackOff);
        return errorHandler;
    }

    /**
     * retry case2. errorhandler에서 retry topic으로 send
     * @KafkaListener(errorHandler = "kafkaErrorHandler") 설정으로 적용
     * KafkaErrorHandler에서 에러발생시 처리 로직 이후
     * sendTo 토픽 발송
     */

    @Bean
    public KafkaListenerErrorHandler kafkaErrorHandler2() {
        return (message, exception ) -> {
            log.error("[KafkaErrorHandler] kafkaMessage=[" + message.getPayload() + "], errorMessage=[" + exception.getMessage() + "]");

            //ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) m.getPayload();
            // 메시지를 더 가공하거나 별도 처리를 하고..


            return message.getPayload();  // sendTo("토픽명")에 입력된 토픽으로 전달 될 메시지 내용
        };
    }
}
