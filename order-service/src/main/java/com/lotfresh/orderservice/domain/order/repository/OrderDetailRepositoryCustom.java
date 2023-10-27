package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.service.response.BestProductsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDetailRepositoryCustom {
    List<OrderDetail> findOrderDetailsByOrderId(Long orderId);
    List<BestProductsResponse> mostSoldProducts(int limitCnt);
    Page<OrderDetail> getRefundsWithPaging(Long userId, Pageable pageable);
}
