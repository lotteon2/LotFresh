package com.lotfresh.orderservice.domain.orchestrator.service.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderCreateResponse {
    Long orderId;
    List<Long> orderDetailIds;
}
