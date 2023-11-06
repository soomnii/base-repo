package com.example.base.subscriber;

// import org.apache.kafka.common.header.Headers;
import com.example.base.model.topic.SampleKafkaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.net.SocketTimeoutException;

@Configuration
public class RetrySubscriber {

    private final Logger log = LoggerFactory.getLogger(RetrySubscriber.class);

    /**
     * retry case1. backoff설정을 통한 retry
     * factory.setCommonErrorHandler(errorHandler());
     * errorHandler에 FixedBackOff()설정하여 재시도 자동 실행하는 case
     *
     */
//    @KafkaListener(
//            topics = "${kafka.topic.object-sample}",
//            containerFactory = "retryJsonListenerContainerFactory")
//    public void createRetyTopic(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//            log.debug("message1111 ===> {}", message);
//            throw new KafkaException("exception");
//    }

    /**
    * retry case2. errorhandler에서 retry topic으로 send
    * @KafkaListener(errorHandler = "kafkaErrorHandler") 설정으로 적용
    * KafkaErrorHandler에서 에러발생시 처리 로직 이후
    * sendTo 토픽 발송
    * */
//    @KafkaListener(
//            topics = "${kafka.topic.object-sample}",
//            containerFactory = "jsonListenerContainerFactory",
//            errorHandler = "kafkaErrorHandler")
//    @SendTo(value = {"retry-topic"})
//    public void SampleObjectListenerWithErrorHandler(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//        log.debug("message2222 ===> {}", message.getName());
//        throw new KafkaException("exception222");
//    }


    /*
    * Retryable 어노테이션 활용 에러 핸들링
    *
    * */
//    @KafkaListener(
//            topics = "${kafka.topic.object-sample}",
//            groupId = "tttttt",
//            containerFactory = "jsonListenerContainerFactory")
//    @Retryable(value=KafkaException.class, maxAttempts = 5, backoff = @Backoff(delay=10000))
//    public void SampleObjectListenerWithBlockingRetry(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//        log.debug("message2222 ===> {}", message.getName());
//        throw new KafkaException("exception222");
//    }

//    @KafkaListener(
//            topics = "retry-topic",
//            containerFactory = "jsonListenerContainerFactory")
//    public void SampleObjectListener2(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//        log.debug("#### retry message ===> {}", message.getName());
//    }
}
