package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;

import java.util.List;

public interface OrderDetailRepositoryCustom {
    List<OrderDetail> findOrderDetailsByOrderId(Long orderId);

}
