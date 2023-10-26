package com.lotfresh.orderservice;

import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ttt {
    @Autowired
    private KafkaProducer kafkaProducer;
    @Test
    public void test() {
        kafkaProducer.send("Inventory",1L);

    }
}
