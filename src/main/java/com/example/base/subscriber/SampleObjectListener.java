package com.example.base.subscriber;

import com.example.base.model.topic.SampleKafkaVO;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class SampleObjectListener {
    @KafkaListener(
            topics = "${kafka.topic.object-sample}",
            containerFactory = "jsonListenerContainerFactory")
    public void SampleObjectListener(
            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
        System.out.println("messag => " + message);
    }
}
