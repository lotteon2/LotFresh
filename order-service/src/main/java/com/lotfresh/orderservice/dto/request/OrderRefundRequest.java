package com.lotfresh.orderservice.dto.request;

import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRefundRequest {
    private ProductOrderId productOrderId;
}
