package com.lotfresh.orderservice.domain.order.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSheetDto {
    private List<OrderSheetItem> orderSheetItems;
    private Boolean isFromCart;
    private Boolean isBargain;
}
