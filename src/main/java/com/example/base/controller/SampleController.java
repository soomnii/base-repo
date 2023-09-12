package com.example.base.controller;

import com.example.base.config.KafkaPub;
import com.example.base.model.topic.KafkaPubVO;
import com.example.base.model.topic.SampleKafkaVO;
import com.example.base.model.topic.SampleKafkaVO2;
import com.example.base.service.AsyncTestService;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@RestController
public class SampleController {

    @Autowired KafkaPub kafkaPub;

    @Value("${kafka.topic.object-sample}")
    String sampleObjectTopic;

    @GetMapping(value = "/test")
    String testKafkaPub() {
        KafkaPubVO pubVo = SampleKafkaVO.builder().id("123").name("234").build();
        kafkaPub.kafkaPublish(sampleObjectTopic, pubVo);
        return "success";
    }

    @GetMapping(value = "/test1")
    String testKafkaPub1() {
        KafkaPubVO pubVo = SampleKafkaVO.builder().id("123").name("234").build();
        kafkaPub.kafkaPublish("multi-event-topic", pubVo);
        return "success";
    }

    @GetMapping(value = "/test2")
    String testKafkaPub2() {
        KafkaPubVO pubVo = SampleKafkaVO2.builder().id("123").name("234").testtt("----").build();
        kafkaPub.kafkaPublish("multi-event-topic", pubVo);
        return "success";
    }

}
