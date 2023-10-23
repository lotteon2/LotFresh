package com.lotfresh.orderservice.domain.order.entity.status;

import lombok.Getter;

@Getter
public enum OrderDetailStatus {
    CANCELED("주문 취소"), CONFIRMED("주문 완료");

    private final String message;

    OrderDetailStatus(String message) {
        this.message = message;
    }


}
