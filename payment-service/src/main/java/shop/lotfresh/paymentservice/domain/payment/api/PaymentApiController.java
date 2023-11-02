package shop.lotfresh.paymentservice.domain.payment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayApproveRequest;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayReadyRequest;
import shop.lotfresh.paymentservice.domain.payment.dto.PaymentInfoResponseDTO;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.service.PaymentService;

import javax.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentApiController {
    private final PaymentService paymentService;

    @PostMapping("/kakaopay/ready")
    public ResponseEntity<String> kakaopayReady(@RequestHeader Long userId,
                                                @Valid @RequestBody KakaopayReadyRequest request) {
        log.warn("헤더에 들어온지 확인해보겠습니다" + userId.toString());

        String redirectPcURl = paymentService.kakaopayReady(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(redirectPcURl);
    }

    @PostMapping("/kakaopay/approve")
    public ResponseEntity<Void> kakaopayApprove(@Valid @RequestBody KakaopayApproveRequest request) {

        try {
            paymentService.kakaopayApprove(request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/orderid/{orderId}")
    public ResponseEntity<PaymentInfoResponseDTO> getPaymentByOrderId(@PathVariable Long orderId) {
        try {
            PaymentInfoResponseDTO paymentInfoResponseDTO = paymentService.getPaymentByOrderId(orderId);
            return ResponseEntity.ok(paymentInfoResponseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}




