package com.example.base.subscriber;

import com.example.base.model.topic.SampleKafkaVO;
import com.example.base.model.topic.SampleKafkaVO2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Configuration
@KafkaListener(id="multiTest", topics = "multi-event-topic", containerFactory = "jsonListenerContainerFactory")
public class MultiHandlerSubscriber {
    private final Logger log = LoggerFactory.getLogger(MultiHandlerSubscriber.class);

    @KafkaHandler
    public void test(SampleKafkaVO sampleTest){
        log.debug("SampleKafkaVO called");
    }

    @KafkaHandler
    public void test(SampleKafkaVO2 sampleTest) {
        log.debug("SampleKafkaVO2 called");
    }



}
