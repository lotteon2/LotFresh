package com.lotfresh.productservice.domain.category;

import com.lotfresh.productservice.domain.category.custom.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {}
