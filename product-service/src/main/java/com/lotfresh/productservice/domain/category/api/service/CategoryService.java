package com.lotfresh.productservice.domain.category.api.service;

import com.lotfresh.productservice.domain.category.api.controller.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.api.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  @Transactional
  public void createCategory(CategoryCreateRequest request) {
    Category parent = null;
    if (request.getParentId() != null) {
      parent =
          categoryRepository
              .findById(request.getParentId())
              .orElseThrow(() -> new CategoryNotFound());
    }
    Category category = request.toEntity(parent);
    categoryRepository.save(category);
  }
}
