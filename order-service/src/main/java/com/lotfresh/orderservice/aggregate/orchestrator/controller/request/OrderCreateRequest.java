package com.lotfresh.orderservice.aggregate.orchestrator.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
    private List<ProductRequest> productRequests;
}
