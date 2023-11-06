package com.example.base.model.topic;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleKafkaVO2 implements Serializable, KafkaPubVO {
    private static final long serialVersionUID = -8279209809719973942L;
    private String id;
    private String name;
    private String testtt;

}
