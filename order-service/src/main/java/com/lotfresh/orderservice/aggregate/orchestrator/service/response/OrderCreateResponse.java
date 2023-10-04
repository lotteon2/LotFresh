package com.lotfresh.orderservice.aggregate.orchestrator.service.response;

import com.lotfresh.orderservice.aggregate.productOrder.domain.ProductOrderId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderCreateResponse {
    Long orderId;
    List<ProductOrderId> productIds;
}
