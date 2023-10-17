package com.lotfresh.orderservice.domain.order.service.response;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailCreateResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Long price;
    private Long quantity;

    public static OrderDetailCreateResponse from(OrderDetail orderDetail) {
        return OrderDetailCreateResponse.builder()
                .id(orderDetail.getId())
                .productId(orderDetail.getProductId())
                .productName(orderDetail.getProductName())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
