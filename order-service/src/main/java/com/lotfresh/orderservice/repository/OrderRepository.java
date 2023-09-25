package com.lotfresh.orderservice.repository;

import com.lotfresh.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
