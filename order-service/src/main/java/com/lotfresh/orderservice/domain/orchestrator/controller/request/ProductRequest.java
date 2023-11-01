package com.lotfresh.orderservice.domain.orchestrator.controller.request;

import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "productId cannot be null")
    private Long productId;
    @NotNull(message = "originalPrice cannot be null")
    private Long originalPrice;
    private Long discountedPrice;
    @NotNull(message = "productStock cannot be null")
    private Long productStock;
    @NotNull(message = "productName cannot be null")
    private String productName;
    @NotNull(message = "producThumbnail cannot be null")
    private String productThumbnail;

    public OrderDetail toEntity(Order order) {
        return OrderDetail.builder()
                .order(order)
                .productId(productId)
                .price(discountedPrice == null ? originalPrice : discountedPrice)
                .stock(productStock)
                .status(OrderDetailStatus.CONFIRMED)
                .productName(productName)
                .productThumbnail(productThumbnail)
                .build();
    }

}
