package com.lotfresh.productservice.domain.category.service;

import com.lotfresh.productservice.domain.category.api.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.api.request.CategoryModifyRequest;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.category.service.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  @Transactional
  public Long createCategory(CategoryCreateRequest request) {
    Category parent = getParentOfNullable(request.getParentId());
    Category category = request.toEntity(parent);
    Category savedCategory = categoryRepository.save(category);
    return savedCategory.getId();
  }

  @Transactional
  public void modifyCategory(CategoryModifyRequest request, Long categoryId) {
    Category parent = getParentOfNullable(request.getParentId());
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFound());
    category.changeCategory(parent, request.getName());
  }

  /**
   * category 논리삭제. flag를 true로 바꾼다.
   *
   * @param categoryId
   */
  @Transactional
  public void softDeleteCategory(Long categoryId) {
    Category category =
        categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFound());
    category.softDelete();
  }

  public CategoryResponse getCategory(Long categoryId) {
    Category category =
        categoryRepository.findByIdFetch(categoryId).orElseThrow(() -> new CategoryNotFound());
    return CategoryResponse.from(category);
  }

  public List<CategoryResponse> getCategories() {
    List<Category> categories = categoryRepository.findAllQuery();
    return categories.stream().map(CategoryResponse::from).collect(Collectors.toList());
  }

  private Category getParentOfNullable(Long parentId) {
    if (parentId == null) {
      return null;
    }
    return categoryRepository.findById(parentId).orElseThrow(() -> new CategoryNotFound());
  }
}
