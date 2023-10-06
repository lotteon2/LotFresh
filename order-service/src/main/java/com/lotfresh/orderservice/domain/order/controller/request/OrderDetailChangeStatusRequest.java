package com.lotfresh.orderservice.domain.order.controller.request;

import com.lotfresh.orderservice.domain.order.entity.OrderDetailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailChangeStatusRequest {
    private Long orderDetailId;
    private OrderDetailStatus orderDetailStatus;
}
