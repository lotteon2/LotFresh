package com.lotfresh.orderservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateRequest {
    private Long userId;
    private List<ProductRequest> productRequests;
    public static OrderCreateRequest forTest(Long userId, List<ProductRequest> productRequests) {
        return new OrderCreateRequest(userId,productRequests);
    }
}
