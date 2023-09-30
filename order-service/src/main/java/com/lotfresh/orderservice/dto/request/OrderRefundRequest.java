package com.lotfresh.orderservice.dto.request;

import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRefundRequest {
    private ProductOrderId productOrderId;
}
