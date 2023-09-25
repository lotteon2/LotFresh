package com.lotfresh.orderservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRequest {
    private Long productId;
    private Long productPrice;
    private Long productQuantity;
}
