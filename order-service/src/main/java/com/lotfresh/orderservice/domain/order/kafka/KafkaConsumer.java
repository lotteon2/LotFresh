package com.lotfresh.orderservice.domain.order.kafka;

import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "refund-success", groupId = "${spring.group-id}")
    public void listenOrderDetailStatus(ConsumerRecord<Long, OrderDetailStatus> record) {
        Long orderDetailId = record.key();
        OrderDetailStatus value = record.value();
        orderService.changeProductOrderStatus(orderDetailId,value);
    }
}
