package com.lotfresh.orderservice.domain.orchestrator.feigns.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaopayReadyRequest {
    private Long orderId;
    private Boolean isFromCart;
    private List<OrderDetailVO> orderDetails;
}
