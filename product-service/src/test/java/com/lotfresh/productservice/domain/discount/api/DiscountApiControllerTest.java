package com.lotfresh.productservice.domain.discount.api;

import com.lotfresh.productservice.ControllerTestSupport;
import com.lotfresh.productservice.domain.discount.api.request.DiscountCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
}
