package com.lotfresh.productservice.domain.product.repository.custom;

import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import org.springframework.data.domain.PageImpl;

import java.util.Optional;

public interface ProductRepositoryCustom {
  Optional<Product> findByIdFetch(Long id);

  PageImpl<Product> findAllByCategory(Long categoryId, PageRequest pageRequest);
}
