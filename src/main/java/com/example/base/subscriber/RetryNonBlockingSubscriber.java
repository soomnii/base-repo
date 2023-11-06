package com.example.base.subscriber;

// import org.apache.kafka.common.header.Headers;
import com.example.base.model.topic.SampleKafkaVO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;

@Configuration
public class RetryNonBlockingSubscriber {

    private final Logger log = LoggerFactory.getLogger(RetryNonBlockingSubscriber.class);

    /*
     * Non-Blocking Retry
     * */
//    @KafkaHandler
//    @RetryableTopic(
//            backoff = @Backoff(value = 5000L),
//            attempts = "3",
//            autoCreateTopics = "false",
//            dltStrategy = DltStrategy.FAIL_ON_ERROR)

//    @RetryableTopic(
//            attempts = "3",
//            backoff = @Backoff(value= 5000L),
//            dltStrategy = DltStrategy.FAIL_ON_ERROR,
//            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
//            kafkaTemplate = "kafkaTemplate")
//    @KafkaListener(
//            topics = "${kafka.topic.object-sample}",
//            containerFactory = "jsonListenerContainerFactory")
//    public void SampleObjectListenerNonBlocking(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//        log.debug("message333 ===> {}", message.getName());
//        throw new KafkaException("exception333");
//    }
//
//    @DltHandler
//    public void dltHandler(ConsumerRecord<String, String> record,
//                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
//                           @Header(KafkaHeaders.OFFSET) Long offset,
//                           @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage) {
//        log.error("dlt received message='{}' with partitionId='{}', offset='{}', topic='{}'", record.value(), offset, partitionId, topic);
////        kafkaConsumerService.saveFailedMessage(topic, partitionId, offset, record.value(), errorMessage);
//    }
}
