package com.lotfresh.productservice.domain.product.service;

import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.category.exception.CategoryNotFound;
import com.lotfresh.productservice.domain.category.repository.CategoryRepository;
import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import com.lotfresh.productservice.domain.product.api.request.ProductModifyRequest;
import com.lotfresh.productservice.domain.product.entity.Product;
import com.lotfresh.productservice.domain.product.exception.ProductNotFound;
import com.lotfresh.productservice.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductServiceTest {
  @Autowired EntityManager em;
  @Autowired ProductService productService;
  @Autowired CategoryRepository categoryRepository;
  @Autowired ProductRepository productRepository;

  @AfterEach
  void tearDown() {
    productRepository.deleteAllInBatch();
    categoryRepository.deleteAllInBatch();
  }

  @DisplayName("상품 정보를 입력 받아 상품을 등록한다.")
  @Test
  void createProduct() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    categoryRepository.saveAll(List.of(category1, category2));
    Long categoryId = category2.getId();
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");

    // when
    Long productId = productService.createProduct(request);

    // then
    assertThat(productId).isNotNull();
  }

  @DisplayName("상품 등록 시 입력 받은 카테고리 id에 해당하는 카테고리가 존재 하지 않으면 예외가 발생한다.")
  @Test
  void createProductWithNoExistCategoryId() throws Exception {
    // given
    Long noExistCategoryId = 0L;
    ProductCreateRequest request =
        new ProductCreateRequest(
            noExistCategoryId, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    // when // then
    assertThatThrownBy(() -> productService.createProduct(request))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  @DisplayName("상품 수정정보를 입력 받아 상품정보를 수정한다.")
  @Test
  void modifyProduct() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));
    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);

    ProductModifyRequest request =
        new ProductModifyRequest(
            category3.getId(), "당근", "modified.jpeg", "modifiedDetail", 1000, "P002");

    // when
    productService.modifyProduct(request, product.getId());

    // then
    Product getProduct = productRepository.findById(product.getId()).get();
    assertThat(getProduct)
        .extracting("name", "thumbnail", "detail", "price", "productCode")
        .containsExactlyInAnyOrder("당근", "modified.jpeg", "modifiedDetail", 1000, "P002");
  }

  @DisplayName("존재하지 않는 상품 수정 시 예외가 발생한다.")
  @Test
  void modifyProductWithNoExistProductId() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));
    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);

    ProductModifyRequest request =
        new ProductModifyRequest(
            category3.getId(), "당근", "modified.jpeg", "modifiedDetail", 1000, "P002");

    Long noExistId = 0L;

    // when // then
    assertThatThrownBy(() -> productService.modifyProduct(request, noExistId))
        .isInstanceOf(ProductNotFound.class)
        .hasMessage("해당 상품이 존재하지 않습니다.");
  }

  @DisplayName("상품 수정 중 존재하지 않는 카테고리 id인 경우 예외가 발생한다.")
  @Test
  void modifyProductWithNoExistCategoryId() throws Exception {
    // given
    Category category1 = createCategory(null, "냉장");
    Category category2 = createCategory(category1, "과일");
    Category category3 = createCategory(category1, "야채");
    categoryRepository.saveAll(List.of(category1, category2, category3));
    Product product = createProduct(category2, "충주사과", "thumbnail.jpeg", "detail", 5000, "P001");
    productRepository.save(product);

    Long noExistCategoryId = 0L;

    ProductModifyRequest request =
        new ProductModifyRequest(
            noExistCategoryId, "당근", "modified.jpeg", "modifiedDetail", 1000, "P002");

    // when // then
    assertThatThrownBy(() -> productService.modifyProduct(request, product.getId()))
        .isInstanceOf(CategoryNotFound.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다.");
  }

  private Product createProduct(
      Category category,
      String name,
      String thumbnail,
      String detail,
      Integer price,
      String productCode) {
    return Product.builder()
        .category(category)
        .name(name)
        .thumbnail(thumbnail)
        .detail(detail)
        .price(price)
        .productCode(productCode)
        .build();
  }

  private Category createCategory(Category parent, String name) {
    return Category.builder().parent(parent).name(name).build();
  }
}
