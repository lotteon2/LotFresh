package com.lotfresh.orderservice.domain.order.service.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    LocalDateTime orderCreatedTime;
    List<OrderDetailResponse> orderDetailResponses;

}
