package com.lotfresh.productservice.domain.product.service;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.lotfresh.productservice.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Transactional
  public Long createProduct(ProductCreateRequest request) {
    Category category =
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(() -> new CategoryNotFound());
    Product savedProduct = request.toEntity(category);
    return savedProduct.getId();
  }
}
