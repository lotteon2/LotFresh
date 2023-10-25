package com.lotfresh.orderservice.domain.orchestrator.feigns.request;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
    private Long productId;
    private Long stock;

    public static ProductInfo from(OrderDetail orderDetail) {
        return ProductInfo.builder()
                .productId(orderDetail.getProductId())
                .stock(orderDetail.getStock())
                .build();
    }

}
