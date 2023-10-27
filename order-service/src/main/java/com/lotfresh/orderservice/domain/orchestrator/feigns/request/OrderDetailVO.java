package com.lotfresh.orderservice.domain.orchestrator.feigns.request;

import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {
    private String productName;
    private Long originalPrice;
    private Long discountedPrice;
    private Long quantity;

    public static OrderDetailVO from(ProductRequest productRequest) {
        return OrderDetailVO.builder()
                .productName(productRequest.getProductName())
                .originalPrice(productRequest.getOriginalPrice())
                .discountedPrice(productRequest.getDiscountedPrice())
                .quantity(productRequest.getProductStock())
                .build();
    }
}
