package com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.orderStep;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.PaymentStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentStatusChanger {
    private final OrderDetailRepository orderDetailRepository;

    public void changeOrderDetailStatus(Long orderId, PaymentStatus status) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderId);
        orderDetails.forEach(orderdetail -> orderdetail.changePaymentStatus(status));
    }
}
