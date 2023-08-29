package com.example.base.subscriber;

// import org.apache.kafka.common.header.Headers;
import com.example.base.model.topic.SampleKafkaVO;
import com.example.base.model.topic.SampleKafkaVO2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

@Configuration
public class SampleObjectSubscriber {

    private final Logger log = LoggerFactory.getLogger(SampleObjectSubscriber.class);

//    @KafkaListener(
//            topics = "${kafka.topic.object-sample}",
//            containerFactory = "jsonListenerContainerFactory")
//    @SendTo(value = {"retry-topic"})
//    public void SampleObjectListener(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//            log.debug("message1111 ===> {}", message);
//            throw new KafkaException("exception");
//    }

    /*
    * KafkaErrorHandler에서 에러발생시 처리 로직 이후
    * sendTo 토픽 발송
    * */
//    @KafkaListener(
//            topics = "${kafka.topic.object-sample}",
//            groupId = "tttttt",
//            containerFactory = "jsonListenerContainerFactory",
//            errorHandler = "kafkaErrorHandler")
//    @SendTo(value = {"retry-topic"})
//    public void SampleObjectListenerWithErrorHandler(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//        log.debug("message2222 ===> {}", message.getName());
//        throw new KafkaException("exception222");
//    }

    @KafkaListener(
            topics = "${kafka.topic.object-sample}",
            groupId = "tttttt",
            containerFactory = "jsonListenerContainerFactory")
    @Retryable(value=KafkaException.class, maxAttempts = 5, backoff = @Backoff(delay=2000))
    public void SampleObjectListenerWithBlockingRetry(
            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
        log.debug("message2222 ===> {}", message.getName());
        throw new KafkaException("exception222");
    }

    /*
     * Non-Blocking Retry
     * */
//    @KafkaListener(
//            topics = "${kafka.topic.object-sample}",
//            groupId = "tttttt",
//            containerFactory = "jsonListenerContainerFactory")
//    @RetryableTopic(
//            backoff = @Backoff(value = 3000L),
//            attempts = "5",
//            autoCreateTopics = "false",
//            dltStrategy = DltStrategy.FAIL_ON_ERROR,
//            include = SocketTimeoutException.class, exclude = NullPointerException.class)
////    @SendTo(value = {"retry-topic"})
//    public void SampleObjectListenerNonBlocking(
//            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
//        log.debug("message333 ===> {}", message.getName());
//        throw new KafkaException("exception333");
//    }

    @KafkaListener(
            topics = "retry-topic",
            containerFactory = "jsonListenerContainerFactory")
    public void SampleObjectListener2(
            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
        log.debug("#### retry message ===> {}", message.getName());
    }
}
