package com.lotfresh.orderservice.dto.response;

import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderCreateResponse {
    Long orderId;
    List<ProductOrderId> productIds;
}
