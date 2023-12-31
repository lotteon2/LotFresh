package com.lotfresh.productservice.domain.discount.service;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import com.lotfresh.productservice.domain.discount.api.request.DiscountModifyRequest;
import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.lotfresh.productservice.domain.discount.exception.DiscountNotFound;
import com.lotfresh.productservice.domain.discount.repository.DiscountRepository;
import com.lotfresh.productservice.domain.discount.service.response.DiscountResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

  @DisplayName("할인 등록 시 카테고리 id는 반드시 존재 해야 한다.")
  @Test
  void createDiscountWithNotExistCategory() {
    // given
    Long notExistCategoryId = 0L;
    LocalDate startDate = LocalDate.of(2023, 9, 29);
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    String imgurl =
        "https://fastly.picsum.photos/id/159/250/250.jpg?hmac=Zx_FJ-m2TaUphHLVrHxHrD5xBHDQhJGvixupBD8YFEE";

    DiscountCreateRequest request =
        new DiscountCreateRequest(notExistCategoryId, 20d, startDate, endDate, imgurl);
    // when // then
    assertThatThrownBy(() -> discountService.createDiscount(request))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  @DisplayName("변경 할 카테고리 할인율과 행사 배너 이미지를 입력받아 해당 할인 정보를 수정한다.")
  @Test
  void modifyDiscount() {
    // given
    Category category = createCategory(null, "냉장");
    categoryRepository.save(category);
    Discount discount =
        createDiscount(category, 20d, LocalDate.of(2023, 9, 29), LocalDate.of(2023, 9, 30), "test");
    discountRepository.save(discount);
    DiscountModifyRequest request = new DiscountModifyRequest(30d, "modify");

    // when
    discountService.modifyDiscount(request, discount.getId());

    // then
    entityManager.clear();
    Discount getDiscount = discountRepository.findById(discount.getId()).get();
    assertThat(getDiscount)
        .extracting("rate", "imgurl")
        .containsExactlyInAnyOrder(request.getRate(), request.getImgurl());
  }

  @DisplayName("카테고리 할인 변경 시 해당 카테고리 할인 id가 존재 하지 않는 경우 예외가 발생한다")
  @Test
  void modifyDiscountWithNoExistDiscountId() {
    // given
    Category category = createCategory(null, "냉장");
    categoryRepository.save(category);
    Long noExistId = 0L;
    DiscountModifyRequest request = new DiscountModifyRequest(30d, "modify");

    // when // then
    assertThatThrownBy(() -> discountService.modifyDiscount(request, noExistId))
        .isInstanceOf(DiscountNotFound.class)
        .hasMessage("해당 카테고리 할인이 존재하지 않습니다.");
  }

  @DisplayName("id로 단일 할인 상세정보를 조회한다.")
  @Test
  void getDiscount() {
    // given
    Category category = createCategory(null, "냉장");
    categoryRepository.save(category);
    Discount discount =
        createDiscount(category, 20d, LocalDate.of(2023, 9, 29), LocalDate.of(2023, 9, 30), "test");
    discountRepository.save(discount);
    Long id = discount.getId();

    // when
    DiscountResponse discountResponse = discountService.getDiscount(id);
    // then
    assertThat(discountResponse)
        .extracting("rate", "imgurl", "startDate", "endDate", "categoryName")
        .containsExactlyInAnyOrder(
            discount.getRate(),
            discount.getImgurl(),
            discount.getStartDate(),
            discount.getEndDate(),
            category.getName());
  }

  @DisplayName("존재하지 않는 할인 id로 조회 시 예외가 발생한다.")
  @Test
  void getDiscountWithNoExistId() {
    // given
    Long noExistId = 0L;

    // when // then
    assertThatThrownBy(() -> discountService.getDiscount(noExistId))
        .isInstanceOf(DiscountNotFound.class)
        .hasMessage("해당 카테고리 할인이 존재하지 않습니다.");
  }

  @DisplayName("카테고리 할인을 전체 조회 한다.")
  @Test
  void getDiscounts() throws Exception {
    // given
    Category category1 = createCategory(null, "냉동");
    Category category2 = createCategory(null, "냉장");
    Category category3 = createCategory(category1, "야채");
    Category category4 = createCategory(category2, "과일");
    Category category5 = createCategory(category2, "정육");
    Category category6 = createCategory(category4, "블루베리");
    categoryRepository.saveAll(
        List.of(category1, category2, category3, category4, category5, category6));

    // 냉동->야채 할인, 냉장->정육 할인, 냉장->과일->블루베리 할인,
    Discount discount1 =
        createDiscount(
            category3, 10d, LocalDate.of(2023, 9, 29), LocalDate.of(2023, 9, 30), "test1");
    Discount discount2 =
        createDiscount(
            category5, 20d, LocalDate.of(2023, 9, 29), LocalDate.of(2023, 9, 30), "test2");
    Discount discount3 =
        createDiscount(
            category6, 20d, LocalDate.of(2023, 9, 29), LocalDate.of(2023, 9, 30), "test3");

    discountRepository.saveAll(List.of(discount1, discount2, discount3));

    // when
    List<DiscountResponse> discounts = discountService.getDiscounts();

    // then
    assertThat(discounts)
        .extracting("rate", "imgurl", "categoryName")
        .containsExactlyInAnyOrder(
            tuple(20d, "test3", "블루베리"), tuple(20d, "test2", "정육"), tuple(10d, "test1", "야채"));
  }

  private Discount createDiscount(
      Category category, Double rate, LocalDate startDate, LocalDate endDate, String imgurl) {
    return Discount.builder()
        .category(category)
        .rate(rate)
        .startDate(startDate)
        .endDate(endDate)
        .imgurl(imgurl)
        .build();
  }

  private Category createCategory(Category parent, String name) {
    return Category.builder().parent(parent).name(name).build();
  }
}
