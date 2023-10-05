package shop.lotfresh.paymentservice.domain.refund.api.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.lotfresh.paymentservice.domain.refund.service.RefundService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refunds")
public class RefundApiController {

    private final RefundService refundService;

    @PostMapping("/order-details")
    public ResponseEntity<Long> createRefund(@Valid @RequestBody CategoryCreateRequest request) {
        Long categoryId = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryId);
    }
}
