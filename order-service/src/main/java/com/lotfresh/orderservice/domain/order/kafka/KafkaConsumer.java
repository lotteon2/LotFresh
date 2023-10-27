package com.lotfresh.orderservice.domain.order.kafka;

import com.lotfresh.orderservice.domain.order.entity.status.DeliveryStatus;
import com.lotfresh.orderservice.domain.order.entity.status.RefundStatus;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "refund-success", groupId = "${spring.kafka.consumer.group-id}")
    public void listenRefundStatus(ConsumerRecord<String, RefundSuccessMessage> record) {
        Long orderDetailId = Long.parseLong(record.key());
        orderService.changeRefundStatus(orderDetailId,RefundStatus.APPROVED);
    }

    @KafkaListener(topics = "delivery")
    public void listenStorageStatus(ConsumerRecord<Long, DeliveryStatus> record){
        Long orderDetailId = record.key();
        DeliveryStatus newDeliveryStatus = record.value();
        orderService.changeDeliveryStatus(orderDetailId,newDeliveryStatus);
    }

}
