package com.lotfresh.orderservice.domain.order.service.response;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long orderDetailId;
    private Long price;
    private Long stock;
    private String status;
    private String productName;
    private String productThumbnail;

    public static OrderDetailResponse from(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .orderDetailId(orderDetail.getId())
                .price(orderDetail.getPrice())
                .stock(orderDetail.getStock())
                .status(orderDetail.getFinalStatusAsString())
                .productName(orderDetail.getProductName())
                .productThumbnail(orderDetail.getProductThumbnail())
                .build();
    }

}
