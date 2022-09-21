package com.example.base.subscriber;

// import org.apache.kafka.common.header.Headers;
import com.example.base.model.topic.SampleKafkaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class SampleObjectSubscriber {

    private final Logger LOGGER = LoggerFactory.getLogger(SampleObjectSubscriber.class);

    @KafkaListener(
            topics = "${kafka.topic.object-sample}",
            containerFactory = "jsonListenerContainerFactory")
    public void SampleObjectListener(
            @Payload SampleKafkaVO message, @Headers MessageHeaders headers) throws Exception {
        LOGGER.debug("message ===> {}", message);
    }
}
