package shop.lotfresh.storageservice.domain.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderProductRequest;
import shop.lotfresh.storageservice.domain.orderproduct.service.OrderProductService;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderProductService orderProductService;

    @KafkaListener(topics="Inventory",groupId = "AAA")
    public void func(ConsumerRecord<String,Object> record) throws Exception{
        OrderProductRequest orderProductRequest1 = objectMapper.readValue((String) record.value(), OrderProductRequest.class);

        orderProductService.inventory(orderProductRequest1.getOrderId());
    }
}