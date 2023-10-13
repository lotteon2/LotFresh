package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderDetailRepositoryCustom {
}
