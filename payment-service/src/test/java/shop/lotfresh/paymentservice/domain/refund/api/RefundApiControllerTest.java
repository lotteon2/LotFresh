package shop.lotfresh.paymentservice.domain.refund.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import shop.lotfresh.paymentservice.ControllerTestSupport;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RefundApiControllerTest extends ControllerTestSupport {
    @DisplayName("Refund 컨트롤러 테스트가 동작한다.")
    @Test
    void controllerTest() {
        System.out.println("Refund 컨트롤러 테스트 내부가 출력된다.");
    }

    @DisplayName("client(소비자)로부터 환불 요청을 받는다.")
    @Test
    void refundCreate() throws Exception {
        Long orderDetailId = 1L;
        RefundCreateRequest request =
                new RefundCreateRequest(5000L, 12L, "KAKAOPAY", "그냥");

        Long mockRefundId = 1L;
        when(refundService.createRefund(anyLong(), any(RefundCreateRequest.class)))
                .thenReturn(mockRefundId);

        mockMvc
                .perform(
                        post("/refunds/order-details/{orderDetailId}", orderDetailId)
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                // Optionally check if the response body contains the expected URL
                .andExpect(content().string(mockRefundId.toString()));

    }

    @DisplayName("환불 요청 헤더에 유저 식별자가 존재해야한다.")
    @Test
    void refundCreateWithoutUserId() throws Exception {
        Long orderDetailId = 1L;
        RefundCreateRequest request =
                new RefundCreateRequest(5000L, 12L, "KAKAOPAY", "그냥");

        Long mockRefundId = 1L;
        when(refundService.createRefund(anyLong(), any(RefundCreateRequest.class)))
                .thenReturn(mockRefundId);

        mockMvc.perform(
                        post("/refunds/order-details/{orderDetailId}", orderDetailId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("환불 요청에 금액이 존재해야한다.")
    @Test
    void refundCreateWithoutAmount() throws Exception {
        Long orderDetailId = 1L;
        RefundCreateRequest request =
                new RefundCreateRequest(null, 12L, "KAKAOPAY", "그냥");

        Long mockRefundId = 1L;
        when(refundService.createRefund(anyLong(), any(RefundCreateRequest.class)))
                .thenReturn(mockRefundId);

        mockMvc
                .perform(
                        post("/refunds/order-details/{orderDetailId}", orderDetailId)
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @DisplayName("환불 요청에 orderId가 존재해야한다.")
    @Test
    void refundCreateWithoutOrderId() throws Exception {
        Long orderDetailId = 1L;
        RefundCreateRequest request =
                new RefundCreateRequest(5000L, null, "KAKAOPAY", "그냥");

        Long mockRefundId = 1L;
        when(refundService.createRefund(anyLong(), any(RefundCreateRequest.class)))
                .thenReturn(mockRefundId);

        mockMvc
                .perform(
                        post("/refunds/order-details/{orderDetailId}", orderDetailId)
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @DisplayName("환불 요청에 기존 결제방식이 존재해야한다.")
    @Test
    void refundCreateWithoutRefundMethod() throws Exception {
        Long orderDetailId = 1L;
        RefundCreateRequest request =
                new RefundCreateRequest(5000L, 12L, null, "그냥");

        Long mockRefundId = 1L;
        when(refundService.createRefund(anyLong(), any(RefundCreateRequest.class)))
                .thenReturn(mockRefundId);

        mockMvc
                .perform(
                        post("/refunds/order-details/{orderDetailId}", orderDetailId)
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @DisplayName("환불 요청에 환불 사유가 존재해야한다.")
    @Test
    void refundCreateWithoutRefundReason() throws Exception {
        Long orderDetailId = 1L;
        RefundCreateRequest request =
                new RefundCreateRequest(5000L, 12L, "KAKAOPAY", null);

        Long mockRefundId = 1L;
        when(refundService.createRefund(anyLong(), any(RefundCreateRequest.class)))
                .thenReturn(mockRefundId);

        mockMvc
                .perform(
                        post("/refunds/order-details/{orderDetailId}", orderDetailId)
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
