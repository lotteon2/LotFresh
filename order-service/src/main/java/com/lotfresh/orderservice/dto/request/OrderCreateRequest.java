package com.lotfresh.orderservice.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderCreateRequest {
    private Long userId;
    private List<ProductRequest> productRequests;
}
