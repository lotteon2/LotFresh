package com.lotfresh.orderservice.domain.order.service.response;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.RefundStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponse {
    private Long orderDetailId;
    private Long price;
    private Long stock;
    private RefundStatus refundStatus;
    private String productName;
    private LocalDateTime refundCreatedAt;
    private LocalDateTime createdAt;
    private Long productId;

    public static RefundResponse from(OrderDetail orderDetail) {
        return RefundResponse.builder()
                .orderDetailId(orderDetail.getId())
                .price(orderDetail.getPrice())
                .stock(orderDetail.getStock())
                .refundStatus(orderDetail.getRefundStatus())
                .productName(orderDetail.getProductName())
                .refundCreatedAt(orderDetail.getRefundCreatedAt())
                .createdAt(orderDetail.getCreatedAt())
                .productId(orderDetail.getProductId())
                .build();
    }

}
