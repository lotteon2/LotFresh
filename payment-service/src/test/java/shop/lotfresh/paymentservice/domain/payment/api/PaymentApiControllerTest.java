package shop.lotfresh.paymentservice.domain.payment.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import shop.lotfresh.paymentservice.ControllerTestSupport;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayReadyRequest;
import shop.lotfresh.paymentservice.domain.payment.vo.OrderDetailVO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentApiControllerTest extends ControllerTestSupport {

    @DisplayName("컨트롤러 테스트가 동작한다.")
    @Test
    void controllerTest() {
        System.out.println("컨트롤러 테스트 내부가 출력된다.");
    }

    @DisplayName("order-service로부터 카카오페이 준비요청을 받는다.")
    @Test
    void kakaopayReady() throws Exception {
        OrderDetailVO orderDetailVO1 = new OrderDetailVO("강원도 고랭지 감자 2KG", 9500L, 3L);
        OrderDetailVO orderDetailVO2 = new OrderDetailVO("홍천 당근 500G", 2500L, 1L);
        OrderDetailVO orderDetailVO3 = new OrderDetailVO("냉동 블루베리 1KG", 8500L, 2L);
        List<OrderDetailVO> orderDetails = Arrays.asList(orderDetailVO1, orderDetailVO2, orderDetailVO3);
        KakaopayReadyRequest request = new KakaopayReadyRequest(1L, orderDetails);

        // Mock the behavior of the payment service
        // 응답으로 오는 redirect qr코드 url
        String mockRedirectUrl = "https://mock.kakao.com/pay";

        when(paymentService.kakaopayReady(anyLong(), any(KakaopayReadyRequest.class)))
                .thenReturn(mockRedirectUrl);

        mockMvc
                .perform(
                        post("/payments/kakaopay/ready")
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                // Optionally check if the response body contains the expected URL
                .andExpect(content().string(mockRedirectUrl));

    }

    @DisplayName("준비요청에 header에 userId가 존재해야한다.")
    @Test
    void kakaopayReadyWithoutHeader() throws Exception {
        OrderDetailVO orderDetailVO1 = new OrderDetailVO("강원도 고랭지 감자 2KG", 9500L, 3L);
        List<OrderDetailVO> orderDetails = List.of(orderDetailVO1);
        KakaopayReadyRequest request = new KakaopayReadyRequest(1L, orderDetails);

        mockMvc
                .perform(
                        post("/payments/kakaopay/ready")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); // Check if the status is 400 Bad Request
    }

    @DisplayName("준비요청 orderDetails 내부에 있는 orderDetailVO의 각 price값은 0 이상의 정수여야한다.")
    @Test
    void kakaopayReadyWithNegativePrice() throws Exception {
        OrderDetailVO orderDetailVO1 = new OrderDetailVO("강원도 고랭지 감자 2KG", 9500L, 3L);
        OrderDetailVO orderDetailVO2 = new OrderDetailVO("홍천 당근 500G", -1L, 1L);
        OrderDetailVO orderDetailVO3 = new OrderDetailVO("냉동 블루베리 1KG", 0L, 2L);
        List<OrderDetailVO> orderDetails = Arrays.asList(orderDetailVO1, orderDetailVO2, orderDetailVO3);
        KakaopayReadyRequest request = new KakaopayReadyRequest(1L, orderDetails);

        mockMvc
                .perform(
                        post("/payments/kakaopay/ready")
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); // Check if the status is 400 Bad Request
    }

    @DisplayName("준비요청 orderDetails 내부에 있는 orderDetailVO의 각 price값은 0 이상의 정수여야한다.")
    @Test
    void kakaopayReadyWithoutPositiveQuantity() throws Exception {
        OrderDetailVO orderDetailVO1 = new OrderDetailVO("강원도 고랭지 감자 2KG", 9500L, 3L);
        OrderDetailVO orderDetailVO2 = new OrderDetailVO("홍천 당근 500G", 1L, 0L);
        OrderDetailVO orderDetailVO3 = new OrderDetailVO("냉동 블루베리 1KG", 0L, -1L);
        List<OrderDetailVO> orderDetails = Arrays.asList(orderDetailVO1, orderDetailVO2, orderDetailVO3);
        KakaopayReadyRequest request = new KakaopayReadyRequest(1L, orderDetails);

        mockMvc
                .perform(
                        post("/payments/kakaopay/ready")
                                .header("userId", "1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest()); // Check if the status is 400 Bad Request
    }


//    @DisplayName("order-service로부터 카카오페이 결제승인요청을 받는다.")
//    @Test
//    void kakaopayApprove() {
//        KakaopayApproveRequest request = new KakaopayApproveRequest(1L, "pgtoken155123kk");
//
//
//
//    }
}

