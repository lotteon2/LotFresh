package com.lotfresh.orderservice.domain.order.redis.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSheetResponse {
    private Long productId;
    private Long originalPrice;
    private Long discountedPrice;
    private Long productStock;
    private String productName;
    private String productThumbnail;
}
