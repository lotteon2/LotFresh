package com.lotfresh.orderservice.domain.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.UserFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.service.OrchestratorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = InsertOrderController.class)
class InsertOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserFeignClient userFeignClient;

    @MockBean
    private OrchestratorService orchestratorService;

    @MockBean
    private KafkaProducer kafkaProducer;

    @DisplayName("주문을 생성한다")
    @Test
    void insertOrder() throws Exception {
        // given
        List<ProductRequest> productRequests  = List.of(
                createProductRequest(1L, 100L, 1L),
                createProductRequest(2L, 500L, 2L),
                createProductRequest(3L, 1000L, 3L),
                createProductRequest(4L, 10000L, 4L)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(productRequests)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderCreateRequest))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @DisplayName("productRequests는 null일 수 없다")
    @Test
    void productRequestsCannotBeNull() throws Exception {
        // given
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(null)
                .build();
        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderCreateRequest))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.productRequests").value("productRequests cannot be null"));

    }

    @DisplayName("productRequests의 size가 0일 수 없다")
    @Test
    void productRequestsSizeIsLargerThanZero() throws Exception {
        // given
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(List.of())
                .build();
        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreateRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.productRequests").value("productRequests cannot be empty"));
    }

    @DisplayName("ProductRequest의 productId, originalPrice, discountedPrice, productStock은 null일 수 없다")
    @Test
    void paramsInProductRequestCannotBeNull() throws Exception {
        // given
        List<ProductRequest> productRequests  = List.of(
                createProductRequest(null, 100L, 1L),
                createProductRequest(2L, null, 2L),
                createProductRequest(3L, 1000L, null),
                createProductRequest(null, null, null)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(productRequests)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreateRequest))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.productId").value("productId cannot be null"))
                .andExpect(jsonPath("$.validation.originalPrice").value("originalPrice cannot be null"))
                .andExpect(jsonPath("$.validation.discountedPrice").value("discountedPrice cannot be null"))
                .andExpect(jsonPath("$.validation.productStock").value("productStock cannot be null"));
    }


    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productStock){
        return ProductRequest.builder()
                .productId(productId)
                .originalPrice(productPrice)
                .discountedPrice(productPrice)
                .productStock(productStock)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .build();
    }

}