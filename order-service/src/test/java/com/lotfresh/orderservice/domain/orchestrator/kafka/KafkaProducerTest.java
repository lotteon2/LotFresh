package com.lotfresh.orderservice.domain.orchestrator.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.groups.Tuple.tuple;

@EmbeddedKafka(
        topics = {"testTopic"},
        ports = 9092,
        brokerProperties = {"listener=PLAINTEXT://localhost:9092"}
)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class KafkaProducerTest {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ObjectMapper mapper;


    @DisplayName("토픽과 값을 인자로 받아 값을 토픽에 전송한다")
    @Test
    public void send(@Autowired EmbeddedKafkaBroker embeddedKafkaBroker){
        // given
        String topic = "testTopic";
        List<InventoryRequest> inventoryRequests = List.of(
                new InventoryRequest(1L,1L,10L),
                new InventoryRequest(2L,2L,10L)
        );
        Object value = inventoryRequests;

        // when
        kafkaProducer.send(topic,value);

        Consumer<String,Object> consumer = makeConsumer(embeddedKafkaBroker);
        consumer.subscribe(Collections.singleton(topic));

        ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(10));

        Assertions.assertThat(records).isNotEmpty();
        for (ConsumerRecord<String, Object> record : records) {
            List<Object> lst = (List<Object>) record.value();
            List<InventoryRequest> results = lst.stream()
                    .map(o -> mapper.convertValue(o, InventoryRequest.class))
                    .collect(Collectors.toList());
            Assertions.assertThat(results)
                    .extracting("orderDetailId","productId","productQuantity")
                    .containsExactlyInAnyOrder(
                            tuple(1L,1L,10L),
                            tuple(2L,2L,10L)
                    );
        }

        consumer.close();

    }

    private Consumer<String,Object> makeConsumer(EmbeddedKafkaBroker embeddedKafkaBroker) {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("localhost:9092", "true", embeddedKafkaBroker.getBrokersAsString());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 컨슈머가 가장 처음부터 메시지를 읽습니다.
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        ConsumerFactory<String, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        return consumerFactory.createConsumer();
    }


}