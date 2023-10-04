package com.lotfresh.orderservice.aggregate.order.controller.request;

import com.lotfresh.orderservice.aggregate.productOrder.domain.ProductOrderId;
import com.lotfresh.orderservice.aggregate.productOrder.domain.ProductOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderChangeStatusRequest {
    private ProductOrderId productOrderId;
    private ProductOrderStatus productOrderStatus;
}
