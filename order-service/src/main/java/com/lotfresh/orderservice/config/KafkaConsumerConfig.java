package com.lotfresh.orderservice.config;

import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.kafka.RefundSuccessMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String SERVER_CONFIG;
    @Value("${spring.kafka.consumer.group-id}")
    private String GROUP_ID;
    @Bean
    public ConsumerFactory<String, RefundSuccessMessage> consumerFactory() {
        Map<String,Object> config = new HashMap<>();

        JsonDeserializer<RefundSuccessMessage> deserializer = new JsonDeserializer<>(RefundSuccessMessage.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_CONFIG);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,RefundSuccessMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RefundSuccessMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
