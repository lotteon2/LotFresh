package com.lotfresh.productservice.domain.discount.service;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DiscountServiceTest {

  @Autowired EntityManager entityManager;
  @Autowired private DiscountService discountService;
  @Autowired private DiscountRepository discountRepository;
  @Autowired private CategoryRepository categoryRepository;

  @AfterEach
  void tearDown() {
    discountRepository.deleteAllInBatch();
    categoryRepository.deleteAllInBatch();
  }

  @DisplayName("할인 정보를 입력받아 카테고리 할인을 등록한다.")
  @Test
  void createDiscount() throws Exception {
    // given
    Category category = createCategory(null, "냉장");
    Category savedCategory = categoryRepository.save(category);
    Long categoryId = savedCategory.getId();
    LocalDate startDate = LocalDate.of(2023, 9, 29);
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    String imgurl =
        "https://fastly.picsum.photos/id/159/250/250.jpg?hmac=Zx_FJ-m2TaUphHLVrHxHrD5xBHDQhJGvixupBD8YFEE";

    DiscountCreateRequest request =
        new DiscountCreateRequest(categoryId, 20d, startDate, endDate, imgurl);

    // when
    Long discountId = discountService.createDiscount(request);

    // then
    entityManager.clear();

    Discount getDiscount = discountRepository.findById(discountId).get();

    assertThat(getDiscount)
        .extracting("rate", "startDate", "endDate", "imgurl")
        .containsExactlyInAnyOrder(20d, startDate, endDate, imgurl);
  }

  private Category createCategory(Category parent, String name) {
    return Category.builder().parent(parent).name(name).build();
  }
}
