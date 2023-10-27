package com.lotfresh.productservice.domain.product.repository;

import com.lotfresh.productservice.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
