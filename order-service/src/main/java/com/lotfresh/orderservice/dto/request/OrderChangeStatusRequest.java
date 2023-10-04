package com.lotfresh.orderservice.dto.request;

import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderChangeStatusRequest {
    private ProductOrderId productOrderId;
    private ProductOrderStatus productOrderStatus;
}
