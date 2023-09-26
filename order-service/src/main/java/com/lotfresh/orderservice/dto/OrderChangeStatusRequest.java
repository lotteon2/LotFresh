package com.lotfresh.orderservice.dto;

import com.lotfresh.orderservice.domain.ProductOrderId;
import com.lotfresh.orderservice.domain.ProductOrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderChangeStatusRequest {
    private ProductOrderId productOrderId;
    private ProductOrderStatus productOrderStatus;

}
