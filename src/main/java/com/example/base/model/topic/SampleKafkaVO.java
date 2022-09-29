package com.example.base.model.topic;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleKafkaVO implements KafkaPubVO {
    String id;
    String name;
}
