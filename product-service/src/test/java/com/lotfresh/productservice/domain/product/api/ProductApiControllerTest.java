package com.lotfresh.productservice.domain.product.api;

import com.lotfresh.productservice.ControllerTestSupport;
import com.lotfresh.productservice.domain.product.api.request.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductApiControllerTest extends ControllerTestSupport {

  @DisplayName("상품을 등록한다.")
  @Test
  void createProduct() throws Exception {
    // given
    Long categoryId = 0L;
    String name = "충주사과";
    String thumbnail = "thumbnail.jpeg";
    String detail = "detail";
    Integer price = 5000;
    String productCode = "P001";
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, name, thumbnail, detail, price, productCode);

    // when // then
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @DisplayName("상품을 등록 시 카테고리 id는 필수 값이다")
  @Test
  void createProductWithNullCategoryId() throws Exception {
    // given
    Long categoryId = null;
    String name = "충주사과";
    String thumbnail = "thumbnail.jpeg";
    String detail = "detail";
    Integer price = 5000;
    String productCode = "P001";
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, name, thumbnail, detail, price, productCode);

    // when // then
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.categoryId").value("categoryId can not be null"));
  }

  @DisplayName("상품을 등록 시 상품이름은 필수 값이다")
  @Test
  void createProductWithEmptyName() throws Exception {
    // given
    Long categoryId = 0L;
    String name = null;
    String thumbnail = "thumbnail.jpeg";
    String detail = "detail";
    Integer price = 5000;
    String productCode = "P001";
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, name, thumbnail, detail, price, productCode);

    // when // then
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.name").value("name can not be empty"));
  }

  @DisplayName("상품을 등록 시 썸네일은 필수 값이다")
  @Test
  void createProductWithEmptyThumbnail() throws Exception {
    // given
    Long categoryId = 0L;
    String name = "충주사과";
    String thumbnail = null;
    String detail = "detail";
    Integer price = 5000;
    String productCode = "P001";
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, name, thumbnail, detail, price, productCode);

    // when // then
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.thumbnail").value("thumbnail can not be empty"));
  }

  @DisplayName("상품을 등록 시 상세정보는 필수 값이다")
  @Test
  void createProductWithEmptyDetail() throws Exception {
    // given
    Long categoryId = 0L;
    String name = "충주사과";
    String thumbnail = "thumbnail.jpeg";
    String detail = null;
    Integer price = 5000;
    String productCode = "P001";
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, name, thumbnail, detail, price, productCode);

    // when // then
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.detail").value("detail can not be empty"));
  }

  @DisplayName("상품을 등록 시 가격은 필수 값이다")
  @Test
  void createProductWithNullPrice() throws Exception {
    // given
    Long categoryId = 0L;
    String name = "충주사과";
    String thumbnail = "thumbnail.jpeg";
    String detail = "detail";
    Integer price = null;
    String productCode = "P001";
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, name, thumbnail, detail, price, productCode);

    // when // then
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.price").value("price can not be null"));
  }

  @DisplayName("상품을 등록 시 상품코드는 필수 값이다")
  @Test
  void createProductWithEmptyProductCode() throws Exception {
    // given
    Long categoryId = 0L;
    String name = "충주사과";
    String thumbnail = "thumbnail.jpeg";
    String detail = "detail";
    Integer price = 5000;
    String productCode = null;
    ProductCreateRequest request =
        new ProductCreateRequest(categoryId, name, thumbnail, detail, price, productCode);

    // when // then
    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.productCode").value("productCode can not be empty"));
  }
}
