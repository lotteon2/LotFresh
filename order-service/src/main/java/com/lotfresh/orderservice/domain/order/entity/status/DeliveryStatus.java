package com.lotfresh.orderservice.domain.order.entity.status;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    READY("배송 준비"), COMPLETE("배송 완료");

    private final String message;

    DeliveryStatus(String message) {
        this.message = message;
    }

}
