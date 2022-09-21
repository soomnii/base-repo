package com.example.base.config;

import com.example.base.model.topic.KafkaPubVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaPub {

    @Autowired KafkaTemplate<String, KafkaPubVO> jsonTemplate;

    @Autowired KafkaTemplate<String, String> stringTemplate;

    public ListenableFuture<SendResult<String, KafkaPubVO>> kafkaPublish(
            String topic, KafkaPubVO kafkaPubVO) {
        return jsonTemplate.send(topic, kafkaPubVO);
    }

    public ListenableFuture<SendResult<String, String>> kafkaPublish(String topic, String message) {
        return stringTemplate.send(topic, message);
    }
}
