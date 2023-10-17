package com.lotfresh.productservice.domain.category.repository.custom;

import com.lotfresh.productservice.domain.category.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {
  Optional<Category> findByIdFetch(Long CategoryId);

  List<Category> findAllQuery();
}
