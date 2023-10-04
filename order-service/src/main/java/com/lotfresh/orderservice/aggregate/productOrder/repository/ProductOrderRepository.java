package com.lotfresh.orderservice.aggregate.productOrder.repository;

import com.lotfresh.orderservice.aggregate.productOrder.domain.ProductOrder;
import com.lotfresh.orderservice.aggregate.productOrder.domain.ProductOrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderId> {
}
