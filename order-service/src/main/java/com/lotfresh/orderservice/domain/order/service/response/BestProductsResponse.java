package com.lotfresh.orderservice.domain.order.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BestProductsResponse {
    private Long productId;
    private Long cnt;
}
