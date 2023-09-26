package com.lotfresh.productservice.domain.category.service;

import com.lotfresh.productservice.domain.category.api.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.api.request.CategoryModifyRequest;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.category.service.response.CategoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
  @Autowired CategoryService categoryService;

  @Autowired CategoryRepository categoryRepository;

  @Autowired EntityManager entityManager;

  @AfterEach
  void tearDown() {
    categoryRepository.deleteAllInBatch();
  }

  @DisplayName("카테고리 이름 정보를 입력 받아 최상위 카테고리를 생성한다.")
  @Test
  void createCategoryWithoutParentCategory() {
    // given
    String inputCategoryName = "냉장";
    CategoryCreateRequest request = new CategoryCreateRequest(null, inputCategoryName);

    // when
    Long savedId = categoryService.createCategory(request);

    // then
    assertThat(savedId).isNotNull();
  }

  @DisplayName("상위 카테고리 ID, 카테고리 이름 정보를 입력 받아 하위 카테고리를 생성한다.")
  @Test
  void createCategoryWithParentCategory() {
    // given
    String inputCategoryName = "냉장";
    Category parentCategory = createCategory();
    Category savedParent = categoryRepository.save(parentCategory);

    CategoryCreateRequest request =
        new CategoryCreateRequest(savedParent.getId(), inputCategoryName);

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
    String inputCategoryName = "과일";
    CategoryCreateRequest request = new CategoryCreateRequest(notExistParentId, "과일");

    // when // then
    assertThatThrownBy(() -> categoryService.createCategory(request))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  @DisplayName("변경 할 상위 카테고리 id, 카테고리 이름을 입력 받아 카테고리 정보를 수정한다.")
  @Test
  void modifyCategory() {
    // given
    String changeName = "야채";
    Category category1 = Category.builder().parent(null).name("냉동").build();
    Category category2 = Category.builder().parent(null).name("냉장").build();
    Category category3 = Category.builder().parent(category2).name("과일").build();
    categoryRepository.saveAll(List.of(category1, category2, category3));
    CategoryModifyRequest request = new CategoryModifyRequest(category1.getId(), changeName);

    // when
    categoryService.modifyCategory(request, category3.getId());

    // then
    // 영속성 컨텍스트 초기화
    entityManager.clear();

    Category getCategory = categoryRepository.findById(category3.getId()).get();
    Long changedParentId = category1.getId();
    assertThat(getCategory)
        .extracting("id", "parent.id", "name")
        .containsExactlyInAnyOrder(category3.getId(), changedParentId, changeName);
  }

  @DisplayName("존재하지 않는 상위 카테고리 아이디로 하위 카테고리 수정 시 예외가 발생한다.")
  @Test
  void modifyCategoryWithNotExistParentId() {
    // given
    Long notExistParentId = 0L;
    String changeName = "야채";
    Category category1 = Category.builder().parent(null).name("냉장").build();
    Category category2 = Category.builder().parent(category1).name("과일").build();
    categoryRepository.saveAll(List.of(category1, category2));
    CategoryModifyRequest request = new CategoryModifyRequest(notExistParentId, changeName);

    // when // then
    // 영속성 컨텍스트 초기화
    entityManager.clear();
    assertThatThrownBy(() -> categoryService.modifyCategory(request, category2.getId()))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  @DisplayName("해당 카테고리의 삭제 플래그를 true로 변경한다. (논리삭제)")
  @Test
  void softDeleteCategory() throws Exception {
    // given
    Category category = createCategory();
    categoryRepository.save(category);
    // when
    categoryService.softDeleteCategory(category.getId());

    // then
    // 영속성 컨텍스트 초기화
    entityManager.clear();
    Category getCategory = categoryRepository.findById(category.getId()).get();
    assertThat(getCategory.getIsDeleted()).isTrue();
  }

  @DisplayName("존재하지 않는 카테고리 아이디로 삭제 시도 시 예외가 발생한다.")
  @Test
  void softDeleteCategoryWithNoCategoryId() throws Exception {
    // given
    Long notExistCategoryId = 0L;

    // when // then
    assertThatThrownBy(() -> categoryService.softDeleteCategory(notExistCategoryId))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  @DisplayName("카테고리 id를 입력 받아 자식 카테고리를 포함한 해당 카테고리 정보를 반환한다.")
  @Test
  void getCategoryById() {
    // given
    Category category1 = Category.builder().parent(null).name("냉동").build();
    Category category2 = Category.builder().parent(null).name("냉장").build();
    Category category3 = Category.builder().parent(category2).name("과일").build();
    Category category4 = Category.builder().parent(category3).name("블루베리").build();
    categoryRepository.saveAll(List.of(category1, category2, category3, category4));

    // when
    CategoryResponse categoryResponse = categoryService.getCategory(category2.getId());

    // then
    assertThat(categoryResponse)
        .extracting("id", "name")
        .containsExactlyInAnyOrder(category2.getId(), category2.getName());

    assertThat(categoryResponse.getChildren())
        .extracting("id", "name")
        .containsExactlyInAnyOrder(tuple(category3.getId(), category3.getName()));
  }

  @DisplayName("존재하지 않는 카테고리 아이디로 조회 시도 시 예외가 발생한다.")
  @Test
  void getCategoryByIdWithNotExistCategoryId() throws Exception {
    // given
    Long notExistCategoryId = 0L;

    // when // then
    assertThatThrownBy(() -> categoryService.getCategory(notExistCategoryId))
            .isInstanceOf(CategoryNotFound.class)
            .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  private Category createCategory() {
    return Category.builder().parent(null).name("냉장").build();
  }
}
