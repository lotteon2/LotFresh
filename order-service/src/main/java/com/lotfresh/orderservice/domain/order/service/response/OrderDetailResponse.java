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
    private Long price;
    private Long quantity;
    private String status;
    private String productName;
    private String productThumbnail;

    public static OrderDetailResponse entityToDto(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .status(orderDetail.getFinalStatusAsString())
                .productName(orderDetail.getProductName())
                .productThumbnail(orderDetail.getProductThumbnail())
                .build();
    }

}
