package com.lotfresh.orderservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {
    private Long productId;
    private Long productPrice;
    private Long productQuantity;
    public static ProductRequest forTest(Long productId, Long productPrice, Long productQuantity){
        return new ProductRequest(productId, productPrice, productQuantity);
    }
}
