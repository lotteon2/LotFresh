package com.lotfresh.productservice.domain.product.repository.custom;

import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {
  Optional<Product> findByIdFetch(Long id);

  Page<Product> findAllByCategory(Long categoryId, PageRequest pageRequest);

  List<Product> findBestProducts(List<Long> ids);
}
