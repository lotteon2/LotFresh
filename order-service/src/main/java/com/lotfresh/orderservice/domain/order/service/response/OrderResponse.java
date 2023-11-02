package com.lotfresh.orderservice.domain.order.service.response;


import com.lotfresh.orderservice.domain.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    Long orderId;

    String orderCreatedTime;
    List<OrderDetailResponse> orderDetailResponses;


    public static OrderResponse from(Order order) {
        String orderCreatedAt = order.getCreatedAt().toString();
        List<OrderDetailResponse> orderDetailResponse = order.getOrderDetails().stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderCreatedTime(orderCreatedAt)
                .orderDetailResponses(orderDetailResponse)
                .build();

    }

}
