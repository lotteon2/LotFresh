package com.lotfresh.productservice.domain.product.repository.custom;

import com.lotfresh.productservice.common.paging.PageRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {
  Optional<Product> findByIdFetch(Long id);

  Page<Product> findAllByCategory(Long categoryId, PageRequest pageRequest);

  List<Product> findAllByIds(List<Long> ids);

  @Cacheable(key = "'all'", value = "newProductsCache")
  List<Product> findNewProductsLimit100();

  Page<Product> findAllByKeyword(PageRequest pageRequest);
}
