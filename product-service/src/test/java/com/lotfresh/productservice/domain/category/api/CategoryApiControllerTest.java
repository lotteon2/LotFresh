package com.lotfresh.productservice.domain.category.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.productservice.domain.category.api.request.CategoryCreateRequest;
import com.lotfresh.productservice.domain.category.api.request.CategoryModifyRequest;
import com.lotfresh.productservice.domain.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CategoryApiController.class})
class CategoryApiControllerTest {
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

  @DisplayName("카테고리를 수정 한다.")
  @Test
  void modifyCategory() throws Exception {
    // given
    Long parentId = 1L;
    Long categoryId = 2L;
    String inputCategoryName = "야채";

    CategoryModifyRequest request = new CategoryModifyRequest(parentId, inputCategoryName);
    // when // then
    mockMvc
        .perform(
            put("/categories/{categoryId}", categoryId)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @DisplayName("카테고리 수정 시 변경 할 카테고리 이름은 필수 값이다.")
  @Test
  void modifyCategoryWithEmptyCategoryName() throws Exception {
    // given
    Long parentId = 1L;
    Long categoryId = 2L;
    String inputCategoryName = "";

    // when // then
    CategoryModifyRequest request = new CategoryModifyRequest(parentId, inputCategoryName);
    // when // then
    mockMvc
        .perform(
            put("/categories/{categoryId}", categoryId)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
        .andExpect(jsonPath("$.validation.name").value("name cannot be null"));
  }

  @DisplayName("카테고리를 논리삭제 한다.")
  @Test
  void softDeleteCategory() throws Exception {
    // given
    Long categoryId = 1L;
    // when // then
    mockMvc
        .perform(patch("/categories/{categoryId}", categoryId))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @DisplayName("카테고리 정보를 조회 한다.")
  @Test
  void getCategory() throws Exception {
    // given
    Long categoryId = 1L;
    // when // then
    mockMvc
            .perform(get("/categories/{categoryId}",categoryId))
            .andDo(print())
            .andExpect(status().isOk());
  }
}
