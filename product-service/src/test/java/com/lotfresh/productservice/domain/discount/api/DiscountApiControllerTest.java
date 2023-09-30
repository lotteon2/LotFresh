package com.lotfresh.productservice.domain.discount.api;

import com.lotfresh.productservice.ControllerTestSupport;
import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DiscountApiControllerTest extends ControllerTestSupport {

  @DisplayName("카테고리 할인을 등록한다.")
  @Test
  void createDiscount() throws Exception {
    // given
    LocalDate startDate = LocalDate.of(2023, 9, 29);
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    DiscountCreateRequest request =
        new DiscountCreateRequest(1L, 20d, startDate, endDate, "https://www");
    // when // then
    mockMvc
        .perform(
            post("/discounts")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @DisplayName("카테고리 할인 등록 시 카테고리 아이디는 필수 값이다.")
  @Test
  void createDiscountWithNullCategoryId() throws Exception {
    // given
    Long categoryId = null;
    LocalDate startDate = LocalDate.of(2023, 9, 29);
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    DiscountCreateRequest request =
        new DiscountCreateRequest(categoryId, 20d, startDate, endDate, "https://www");
    // when // then
    mockMvc
        .perform(
            post("/discounts")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.categoryId").value("categoryId can not be null"));
  }

  @DisplayName("카테고리 할인 등록 시 할인율는 필수 값이다.")
  @Test
  void createDiscountWithNullRate() throws Exception {
    // given
    Long categoryId = 1L;
    Double rate = null;
    LocalDate startDate = LocalDate.of(2023, 9, 29);
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    DiscountCreateRequest request =
            new DiscountCreateRequest(categoryId, null, startDate, endDate, "https://www");
    // when // then
    mockMvc
            .perform(
                    post("/discounts")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(jsonPath("$.validation.rate").value("rate can not be null"));
  }

  @DisplayName("카테고리 할인 등록 시 시작일자는 필수 값이다.")
  @Test
  void createDiscountWithNullStartDate() throws Exception {
    // given
    Long categoryId = 1L;
    Double rate = 20d;
    LocalDate startDate = null;
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    DiscountCreateRequest request =
            new DiscountCreateRequest(categoryId,   rate, startDate, endDate, "https://www");
    // when // then
    mockMvc
            .perform(
                    post("/discounts")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(jsonPath("$.validation.startDate").value("startDate can not be null"));
  }

  @DisplayName("카테고리 할인 등록 시 종료일자는 필수 값이다.")
  @Test
  void createDiscountWithNullEndDate() throws Exception {
    // given
    Long categoryId = 1L;
    Double rate = 20d;
    LocalDate startDate = LocalDate.of(2023, 9, 29);
    LocalDate endDate = null;
    DiscountCreateRequest request =
            new DiscountCreateRequest(categoryId, null, startDate, endDate, "https://www");
    // when // then
    mockMvc
            .perform(
                    post("/discounts")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(jsonPath("$.validation.endDate").value("endDate can not be null"));
  }

  @DisplayName("카테고리 할인 등록 시 배너 이미지는 필수 값이다.")
  @Test
  void createDiscountWithEmptyImgurl() throws Exception {
    // given
    Long categoryId = 1L;
    Double rate = 20d;
    LocalDate startDate = LocalDate.of(2023, 9, 29);
    LocalDate endDate = LocalDate.of(2023, 9, 30);
    String imgurl = null;
    DiscountCreateRequest request =
            new DiscountCreateRequest(categoryId, null, startDate, endDate, imgurl);
    // when // then
    mockMvc
            .perform(
                    post("/discounts")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(jsonPath("$.validation.imgurl").value("imgurl can not be empty"));
  }
}
