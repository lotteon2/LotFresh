package com.lotfresh.orderservice.domain.order.entity.status;

import lombok.Getter;

@Getter
public enum RefundStatus {
    READY("환불 승인 대기중"), APPROVED("환불 완료"), REJECTED("환불 거절");
    private final String message;

    RefundStatus(String message) {
        this.message = message;
    }
}