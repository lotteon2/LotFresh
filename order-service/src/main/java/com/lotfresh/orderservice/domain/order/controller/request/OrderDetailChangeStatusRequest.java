package com.lotfresh.orderservice.domain.order.controller.request;

import com.lotfresh.orderservice.domain.order.entity.OrderDetailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailChangeStatusRequest {
    @NotNull(message = "orderDetailId cannot be null")
    private Long orderDetailId;
    @NotNull(message = "orderDetailStatus cannot be null")
    private OrderDetailStatus orderDetailStatus;
}
