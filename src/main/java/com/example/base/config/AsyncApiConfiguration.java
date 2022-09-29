package com.example.base.config;

import com.asyncapi.v2.model.info.Info;
import com.asyncapi.v2.model.server.Server;
import com.example.base.model.topic.KafkaPubVO;
import io.github.stavshamir.springwolf.asyncapi.types.KafkaProducerData;
import io.github.stavshamir.springwolf.configuration.AsyncApiDocket;
import io.github.stavshamir.springwolf.configuration.EnableAsyncApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAsyncApi
public class AsyncApiConfiguration {

    private final String BOOTSTRAP_SERVERS;

    private final String PRODUCER_TOPIC;

    private final String CONSUMER_TOPIC;

    public AsyncApiConfiguration(
            @Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapServers,
            @Value("${kafka.topic.object-sample}") String objectSmapleTopic) {
        this.BOOTSTRAP_SERVERS = bootstrapServers;
        this.PRODUCER_TOPIC = objectSmapleTopic;
        this.CONSUMER_TOPIC = objectSmapleTopic;
    }

    @Bean
    public AsyncApiDocket asyncApiDocket() {
        Info info = Info.builder().version("1.0.0").title("Shinhan meta project - Kafka").build();

        //        /* 0.7version
        KafkaProducerData exampleProducerData =
                KafkaProducerData.kafkaProducerDataBuilder()
                        .topicName(PRODUCER_TOPIC)
                        .payloadType(KafkaPubVO.class)
                        .build();

        //               KafkaConsumerData manuallyConfiguredConsumer =
        //        KafkaConsumerData.kafkaConsumerDataBuilder()
        //                       .topicName(CONSUMER_TOPIC)
        //                       .description("Custom, optional description for this consumed
        // topic")
        //                       .payloadType(KafkaPubVO.class)
        //                       .build();

        return AsyncApiDocket.builder()
                .basePackage("com.example.base.subscriber")
                .info(info)
                .server("kafka", Server.builder().protocol("kafka").url(BOOTSTRAP_SERVERS).build())
                //                       .producer(exampleProducerData)
                .build();

        //        ProducerData exampleProducerData =
        //                ProducerData.builder()
        //                        .channelName(PRODUCER_TOPIC)
        //                        .payloadType(SampleKafkaVO.class)
        //                        .build();
        //
        //        return AsyncApiDocket.builder()
        //                .basePackage("com.example.base.subscriber")
        //                .info(info)
        //                .server("kafka",
        // Server.builder().protocol("kafka").url(BOOTSTRAP_SERVERS).build())
        ////                .producer(exampleProducerData)
        //                .build();
    }
}
