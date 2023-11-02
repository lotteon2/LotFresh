package shop.lotfresh.paymentservice.domain.refund.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import shop.lotfresh.paymentservice.domain.payment.dto.PaymentInfoResponseDTO;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.dto.RefundInfoListDTO;
import shop.lotfresh.paymentservice.domain.refund.dto.RefundInfoResponseDTO;
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

    @GetMapping("/{refundId}")
    public ResponseEntity<RefundInfoResponseDTO> getRefundInfoById(@PathVariable Long refundId) {
        try {
            RefundInfoResponseDTO refundInfoResponseDTO = refundService.getRefundDetailByRefundId(refundId);
            return ResponseEntity.ok(refundInfoResponseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping()
    public ResponseEntity<RefundInfoListDTO> getReadyRefunds(@RequestParam(defaultValue = "0") Long page,
                                                             @RequestParam(defaultValue = "10") Long size) {
        try {
            RefundInfoListDTO refundInfoResponseDTO = refundService.getReadyRefunds(page, size);
            return ResponseEntity.ok(refundInfoResponseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
