package com.lotfresh.orderservice.repository;

import com.lotfresh.orderservice.domain.ProductOrder;
import com.lotfresh.orderservice.domain.ProductOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderId> {
}
