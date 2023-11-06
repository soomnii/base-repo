//package com.example.base.config;
//
//import com.example.base.model.topic.KafkaPubVO;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.listener.DefaultErrorHandler;
//import org.springframework.kafka.listener.KafkaListenerErrorHandler;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.util.backoff.BackOff;
//import org.springframework.util.backoff.FixedBackOff;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@EnableKafka
//@Configuration
//public class DeserializeConsumerConfig {
//
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    String bootstrapServers;
//
//    @Value(value = "${spring.kafka.consumer.group-id}")
//    String groupId;
//
//
//    @Autowired KafkaTemplate<String, KafkaPubVO> jsonTemplate;
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO>
//            retryJsonListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, KafkaPubVO> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(getJsonConsumerFactory(getJsonConsumerFactoryConfig()));
//        factory.setReplyTemplate(jsonTemplate);
////        factory.getContainerProperties()
////                .setAckMode(ContainerProperties.AckMode.RECORD);
////        factory.setCommonErrorHandler(errorHandler());
//        return factory;
//    }
//
//    public DefaultKafkaConsumerFactory<String, KafkaPubVO> getJsonConsumerFactory(
//            Map<String, Object> config) {
//        return new DefaultKafkaConsumerFactory<>(
//                config,
//                new StringDeserializer(),
//                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(KafkaPubVO.class, false)));
//    }
//
//    public Map<String, Object> getJsonConsumerFactoryConfig() {
//        Map<String, Object> consumerConfig = new HashMap<>();
//        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
////        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, );
//        //consumerConfig.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, true);
////        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
////        consumerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
//        return consumerConfig;
//    }
//
//    @Bean
//    public KafkaListenerErrorHandler kafkaErrorHandler2() {
//        return (message, exception ) -> {
//            log.error("[KafkaErrorHandler] kafkaMessage=[" + message.getPayload() + "], errorMessage=[" + exception.getMessage() + "]");
//
//            //ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) m.getPayload();
//            // 메시지를 더 가공하거나 별도 처리를 하고..
//
//            return message.getPayload();  // sendTo("토픽명")에 입력된 토픽으로 전달 될 메시지 내용
//        };
//    }
//
//    @Bean
//    public DefaultErrorHandler errorHandler() {
//        log.debug("is this innn????");
//        BackOff fixedBackOff = new FixedBackOff(5000, 3);
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
//            // logic to execute when all the retry attemps are exhausted
//        }, fixedBackOff);
//        return errorHandler;
//    }
//}
