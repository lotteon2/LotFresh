package shop.lotfresh.storageservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderProductRequest;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerConfig {
    private String SERVER_CONFIG = "localhost:9092";
    private String GROUP_ID = "AAA";

    @Bean
    public ConsumerFactory<String, OrderProductRequest> consumerFactory() {
        Map<String,Object> config = new HashMap<>();

        JsonDeserializer<OrderProductRequest> deserializer = new JsonDeserializer<>(OrderProductRequest.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER_CONFIG);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,GROUP_ID);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,OrderProductRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderProductRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}