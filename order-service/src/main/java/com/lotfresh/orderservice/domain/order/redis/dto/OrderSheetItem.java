package com.lotfresh.orderservice.domain.order.redis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSheetItem {
    private Long productId;
    private Long originalPrice;
    private Long discountedPrice;
    private Long productStock;
    private String productName;
    private String productThumbnail;
}
