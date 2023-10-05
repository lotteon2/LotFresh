package com.lotfresh.productservice.domain.product.repository.custom;

import com.lotfresh.productservice.domain.product.entity.Product;

import java.util.Optional;

public interface ProductRepositoryCustom {
    Optional<Product> findByIdFetch(Long id);
}
