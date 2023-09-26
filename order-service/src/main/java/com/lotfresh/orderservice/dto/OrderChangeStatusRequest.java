package com.lotfresh.orderservice.dto;

import com.lotfresh.orderservice.domain.ProductOrderId;
import com.lotfresh.orderservice.domain.ProductOrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderChangeStatusRequest {
    private ProductOrderId productOrderId;
    private ProductOrderStatus productOrderStatus;

    public static OrderChangeStatusRequest forTest(ProductOrderId productOrderId,
                                                   ProductOrderStatus productOrderStatus) {
        return new OrderChangeStatusRequest(productOrderId,productOrderStatus);
    }

}
