package com.example.base.subscriber;

// import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class SampleSubscriber {

    @KafkaListener(
            topics = "${kafka.topic.sample}",
            containerFactory = "stringListenerContainerFactory")
    public void SampleListener(@Payload String message, @Headers MessageHeaders headers)
            throws Exception {
        System.out.println("messag => " + message);
    }
}
