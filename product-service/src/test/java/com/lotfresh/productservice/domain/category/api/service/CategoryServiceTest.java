package com.lotfresh.productservice.domain.category.api.service;

import com.lotfresh.productservice.domain.category.api.controller.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.api.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    CategoryCreateRequest request = new CategoryCreateRequest(savedParent.getId(), "블루베리");

    // when
    Long savedId = categoryService.createCategory(request);

    // then
    assertThat(savedId).isNotNull();
  }

  @DisplayName("존재하지 않는 상위 카테고리 아이디로 하위 카테고리 생성 시 예외가 발생한다.")
  @Test
  void createCategoryWithNotExistParentId() {
    // given
    Long notExistParentId = 0L;
    CategoryCreateRequest request = new CategoryCreateRequest(notExistParentId, "블루베리");

    // when // then
    assertThatThrownBy(() -> categoryService.createCategory(request))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  private Category createCategory() {
    return Category.builder().parent(null).name("냉장").build();
  }
}
