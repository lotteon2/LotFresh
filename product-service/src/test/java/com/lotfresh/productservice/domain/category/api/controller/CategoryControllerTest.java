package com.lotfresh.productservice.domain.category.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.productservice.domain.category.api.controller.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.api.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CategoryController.class})
class CategoryControllerTest {
  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @MockBean CategoryService categoryService;

  @DisplayName("카테고리를 등록한다.")
  @Test
  void createCategory() throws Exception {
    // given
    CategoryCreateRequest request = new CategoryCreateRequest(null, "냉장");
    // when // then
    mockMvc
        .perform(
            post("/categories")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @DisplayName("카테고리 등록 시 반드시 카테고리 이름이 존재해야 한다.")
  @Test
  void createCategoryWithEmptyCategoryName() throws Exception {
    // given
    CategoryCreateRequest request = new CategoryCreateRequest(null, "");
    // when // then
    mockMvc
        .perform(
            post("/categories")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.name").value("name cannot be null"));
  }
}
