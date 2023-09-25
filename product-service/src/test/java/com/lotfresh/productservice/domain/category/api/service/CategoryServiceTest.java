package com.lotfresh.productservice.domain.category.api.service;

import com.lotfresh.productservice.domain.category.api.controller.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CategoryServiceTest {
  @Autowired CategoryService categoryService;

  @Autowired CategoryRepository categoryRepository;

  @AfterEach
  void tearDown() {
    categoryRepository.deleteAllInBatch();
  }

  @DisplayName("카테고리 이름 정보를 입력 받아 최상위 카테고리를 생성한다.")
  @Test
  void createCategoryWithoutParentCategory() {
    // given
    CategoryCreateRequest request = new CategoryCreateRequest(null, "냉장");

    // when
    Long savedId = categoryService.createCategory(request);

    // then
    assertThat(savedId).isNotNull();
  }

  @DisplayName("상위 카테고리 ID, 카테고리 이름 정보를 입력 받아 하위 카테고리를 생성한다.")
  @Test
  void createCategoryWithParentCategory() {
    // given
    Category parentCategory = createCategory();
    Category savedParent = categoryRepository.save(parentCategory);

    CategoryCreateRequest request = new CategoryCreateRequest(savedParent.getId() , "블루베리");

    // when
    Long savedId = categoryService.createCategory(request);

    // then
    assertThat(savedId).isNotNull();
  }


  private Category createCategory() {
    return Category.builder().parent(null).name("냉장").build();
  }
}
