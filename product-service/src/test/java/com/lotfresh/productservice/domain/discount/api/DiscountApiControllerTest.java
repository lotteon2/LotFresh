package com.lotfresh.productservice.domain.discount.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.productservice.domain.category.api.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.entity.Category;
import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import com.lotfresh.productservice.domain.discount.service.DiscountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {DiscountApiController.class})
class DiscountApiControllerTest {
  @Autowired protected MockMvc mockMvc;

  @Autowired protected ObjectMapper objectMapper;

  @MockBean protected DiscountService discountService;

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
}
