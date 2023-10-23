package com.lotfresh.orderservice.domain.order.entity.status;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    READY("결제 대기"), CANCELED("결제 취소"), FAILED("결제 실패"), SUCCESS("결제 완료");

    private final String message;

    PaymentStatus(String message) {
        this.message = message;
    }
}