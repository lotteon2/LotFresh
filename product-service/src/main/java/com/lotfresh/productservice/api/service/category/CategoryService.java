package com.lotfresh.productservice.api.service.category;

import com.lotfresh.productservice.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
}
