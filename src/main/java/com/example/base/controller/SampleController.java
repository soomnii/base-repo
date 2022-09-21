package com.example.base.controller;

import com.example.base.config.KafkaPub;
import com.example.base.model.topic.KafkaPubVO;
import com.example.base.model.topic.SampleKafkaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
