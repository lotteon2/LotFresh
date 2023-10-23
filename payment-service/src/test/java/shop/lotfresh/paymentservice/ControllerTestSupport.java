package shop.lotfresh.paymentservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import shop.lotfresh.paymentservice.domain.payment.api.PaymentApiController;
import shop.lotfresh.paymentservice.domain.payment.service.PaymentService;
import shop.lotfresh.paymentservice.domain.refund.api.RefundApiController;
import shop.lotfresh.paymentservice.domain.refund.service.RefundService;

@WebMvcTest(controllers = {PaymentApiController.class, RefundApiController.class})
public class ControllerTestSupport {

    @Autowired protected MockMvc mockMvc;

    @Autowired protected ObjectMapper objectMapper;

    @MockBean protected PaymentService paymentService;

    @MockBean protected RefundService refundService;
}
