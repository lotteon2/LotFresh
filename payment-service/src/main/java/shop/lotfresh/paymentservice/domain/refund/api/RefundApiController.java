package shop.lotfresh.paymentservice.domain.refund.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.service.RefundService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/refunds")
public class RefundApiController {

    private final RefundService refundService;

    @PostMapping("/order-details/{orderDetailId}")
    public ResponseEntity<Long> createRefund(@PathVariable Long orderDetailId,
                                             @Valid @RequestBody RefundCreateRequest request,
                                             @RequestHeader(value = "userId", required = false) String userId) {

        if (userId == null || userId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User id is missing in the header");
        }

        Long refundId = refundService.createRefund(orderDetailId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(refundId);
    }

    @PatchMapping("/{refundId}/approve")
    public ResponseEntity<Void> approveRefund(@PathVariable Long refundId) {
        try {
            refundService.approveRefund(refundId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {

            log.warn(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{refundId}/reject")
    public ResponseEntity<Void> rejectRefund(@PathVariable Long refundId) {
        refundService.rejectRefund(refundId);
        return ResponseEntity.ok().build();
    }
}
