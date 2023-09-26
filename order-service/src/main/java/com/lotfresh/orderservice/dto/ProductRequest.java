package com.lotfresh.orderservice.dto;

import com.lotfresh.orderservice.domain.Order;
import com.lotfresh.orderservice.domain.ProductOrder;
import com.lotfresh.orderservice.domain.ProductOrderId;
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

    public ProductOrder toEntity(Order order) {
        return ProductOrder.builder()
                .id(buildProductOrderId(productId))
                .order(order)
                .price(productPrice)
                .quantity(productQuantity)
                .build();
    }
    private ProductOrderId buildProductOrderId(Long productId) {
        return ProductOrderId.builder()
                .productId(productId)
                .build();
    }

}
