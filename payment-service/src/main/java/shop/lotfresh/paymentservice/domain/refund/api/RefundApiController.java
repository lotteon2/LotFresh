package shop.lotfresh.paymentservice.domain.refund.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.service.RefundService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refunds")
public class RefundApiController {

    private final RefundService refundService;

    @PostMapping("/order-details/{orderDetailId}")
    public ResponseEntity<Long> createRefund(@PathVariable Long orderDetailId,
                                             @Valid @RequestBody RefundCreateRequest request) {

        Long refundId = refundService.createRefund(orderDetailId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(refundId);
    }
}
