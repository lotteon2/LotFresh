package com.lotfresh.orderservice.dto.request;

import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderStatus;
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
