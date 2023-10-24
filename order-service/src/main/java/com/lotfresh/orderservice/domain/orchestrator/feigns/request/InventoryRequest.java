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
public class InventoryRequest {
    private Long orderDetailId;
    private Long productId;
    private Long productStock;

    public static InventoryRequest from(OrderDetail orderDetail) {
        return InventoryRequest.builder()
                .orderDetailId(orderDetail.getId())
                .productId(orderDetail.getProductId())
                .productStock(orderDetail.getStock())
                .build();
    }

}
