package com.lotfresh.orderservice.domain.order.entity.status;

import com.lotfresh.orderservice.domain.order.kafka.RefundSuccessMessage;
import lombok.Getter;

@Getter
public enum RefundStatus {
    CREATED("환불 요청 없음"), READY("환불 승인 대기중"), APPROVED("환불 완료"), REJECTED("환불 거절");

    private final String message;

    RefundStatus(String message) {
        this.message = message;
    }
}