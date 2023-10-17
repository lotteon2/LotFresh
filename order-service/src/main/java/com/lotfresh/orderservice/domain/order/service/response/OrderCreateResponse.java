package com.lotfresh.orderservice.domain.order.service.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderCreateResponse {
    private Long orderId;
    private List<OrderDetailCreateResponse> orderDetailCreateResponses;
}
