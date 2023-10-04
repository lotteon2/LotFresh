package com.lotfresh.orderservice.aggregate.order.repository;

import com.lotfresh.orderservice.aggregate.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
