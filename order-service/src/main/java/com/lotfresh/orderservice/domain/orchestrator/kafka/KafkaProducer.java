package com.lotfresh.orderservice.domain.orchestrator.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<Long,Object> kafkaTemplate;

    public void send(String topic, Object value){
        kafkaTemplate.send(topic,value);
    }
    public void send(String topic, Long key, Object value){
        kafkaTemplate.send(topic,key,value);
    }
}
