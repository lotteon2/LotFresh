package shop.lotfresh.paymentservice.domain.payment.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayApproveRequest;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayReadyRequest;
import shop.lotfresh.paymentservice.domain.payment.service.PaymentService;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentApiController {
    private final PaymentService paymentService;

    @PostMapping("/kakaopay/ready")
    public ResponseEntity<String> kakaopayReady(@RequestHeader Long userId,
                                                @Valid @RequestBody KakaopayReadyRequest request) {

        String redirectPcURl = paymentService.kakaopayReady(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(redirectPcURl);
    }

    @PostMapping("/kakaopay/approve")
    public ResponseEntity<Void> kakaopayApprove(@Valid @RequestBody KakaopayApproveRequest request) {

        paymentService.kakaopayApprove(request);
        return ResponseEntity.ok().build();
    }
}




