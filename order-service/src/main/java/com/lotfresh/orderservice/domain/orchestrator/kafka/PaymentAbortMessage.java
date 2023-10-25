package com.lotfresh.orderservice.domain.orchestrator.kafka;

import com.lotfresh.orderservice.domain.order.entity.status.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PaymentAbortMessage {
    private PaymentStatus status;
}
