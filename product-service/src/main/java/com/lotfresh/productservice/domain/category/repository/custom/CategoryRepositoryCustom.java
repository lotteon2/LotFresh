package com.lotfresh.productservice.domain.category.repository.custom;

import com.lotfresh.productservice.domain.category.entity.Category;

import java.util.Optional;

public interface CategoryRepositoryCustom {
  Optional<Category> findByIdQuery(Long CategoryId);
}
