package com.example.base.model.topic;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SampleKafkaVO implements Serializable, KafkaPubVO {
    private static final long serialVersionUID = 3966101970287166865L;
    String id;
    String name;
}
