package com.lotfresh.orderservice.domain.orchestrator.controller.request;

import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.OrderDetailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long productId;
    private Long productPrice;
    private Long productQuantity;

    public OrderDetail toEntity(Order order) {
        return OrderDetail.builder()
                .order(order)
                .productId(productId)
                .price(productPrice)
                .quantity(productQuantity)
                .status(OrderDetailStatus.CREATED)
                .build();
    }

}
