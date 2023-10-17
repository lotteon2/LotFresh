package com.lotfresh.orderservice.domain.orchestrator.feigns.request;

import com.lotfresh.orderservice.domain.order.service.response.OrderDetailCreateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {
    private String productName;
    private Long price;
    private Long quantity;

    public static OrderDetailVO from(OrderDetailCreateResponse orderDetailCreateResponse) {
        return OrderDetailVO.builder()
                .productName(orderDetailCreateResponse.getProductName())
                .price(orderDetailCreateResponse.getPrice())
                .quantity(orderDetailCreateResponse.getQuantity())
                .build();
    }
}
