package com.lotfresh.orderservice.dto.request;

import com.lotfresh.orderservice.domain.order.Order;
import com.lotfresh.orderservice.domain.productOrder.ProductOrder;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long productId;
    private Long productPrice;
    private Long productQuantity;

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
