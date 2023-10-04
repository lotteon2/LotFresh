package com.lotfresh.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.productservice.domain.category.api.CategoryApiController;
import com.lotfresh.productservice.domain.category.service.CategoryService;
import com.lotfresh.productservice.domain.discount.api.DiscountApiController;
import com.lotfresh.productservice.domain.discount.service.DiscountService;
import com.lotfresh.productservice.domain.product.api.ProductApiController;
import com.lotfresh.productservice.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {CategoryApiController.class, DiscountApiController.class, ProductApiController.class})
public class ControllerTestSupport {

  @Autowired protected MockMvc mockMvc;

  @Autowired protected ObjectMapper objectMapper;

  @MockBean protected CategoryService categoryService;

  @MockBean protected DiscountService discountService;

  @MockBean protected ProductService productService;
}
