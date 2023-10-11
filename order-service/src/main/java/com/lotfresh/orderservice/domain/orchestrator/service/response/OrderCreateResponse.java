package com.lotfresh.orderservice.domain.orchestrator.service.response;

import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderCreateResponse {
    private Order order;
    private List<OrderDetail> orderDetails;
}
