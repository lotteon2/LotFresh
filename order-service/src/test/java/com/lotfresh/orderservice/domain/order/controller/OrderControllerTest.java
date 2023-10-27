package com.lotfresh.orderservice.domain.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @DisplayName("주문 상세내역의 Status를 변경한다")
    @Test
    void changeStatus() throws Exception {
        // given
        OrderDetailChangeStatusRequest request = OrderDetailChangeStatusRequest.builder()
                .orderDetailId(1L)
                .orderDetailStatus(OrderDetailStatus.CONFIRMED)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/order/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @DisplayName("주문 상세내역의 상태변경 시 Id는 필수값이다")
    @Test
    void orderDetailIdCannotBeNullWhenChangeStatus() throws Exception {
        // given
        OrderDetailChangeStatusRequest request = OrderDetailChangeStatusRequest.builder()
                .orderDetailId(null)
                .orderDetailStatus(OrderDetailStatus.CONFIRMED)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/order/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.orderDetailId").value("orderDetailId cannot be null"));
    }

    @DisplayName("주문 상세내역의 상태변경 시 상태는 필수값이다")
    @Test
    void orderDetailStatusCannotBeNullWhenChangeStatus() throws Exception {
        // given
        OrderDetailChangeStatusRequest request = OrderDetailChangeStatusRequest.builder()
                .orderDetailId(1L)
                .orderDetailStatus(null)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/order/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.orderDetailStatus").value("orderDetailStatus cannot be null"));
    }

    @DisplayName("주문 상세내역의 상태변경 시 Id와 상태는 필수값이다")
    @Test
    void orderDetailIdAndStatusCannotBeNullWhenChangeStatus() throws Exception {
        // given
        OrderDetailChangeStatusRequest request = OrderDetailChangeStatusRequest.builder()
                .orderDetailId(null)
                .orderDetailStatus(null)
                .build();

        // when // then
        mockMvc.perform(MockMvcRequestBuilders.patch("/order/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.orderDetailId").value("orderDetailId cannot be null"))
                .andExpect(jsonPath("$.validation.orderDetailStatus").value("orderDetailStatus cannot be null"));
    }

    @DisplayName("음수의 page값으로는 PageRequest를 만들 수 없다")
    @Test
    void CannotMakePageRequestWithNegativePageValue() {
        // given
        int page = -1;
        int size = 5;

        // when // then
        Assertions.assertThatThrownBy(() -> PageRequest.of(page, size))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Page index must not be less than zero");

    }


}