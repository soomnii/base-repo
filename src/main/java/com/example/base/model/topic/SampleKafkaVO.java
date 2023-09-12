package com.example.base.model.topic;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleKafkaVO implements Serializable, KafkaPubVO {
    private static final long serialVersionUID = 3966101970287166865L;
    private String id;
    private String name;
}
