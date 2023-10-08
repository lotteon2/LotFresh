package shop.lotfresh.paymentservice.domain.refund.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.repository.PaymentRepository;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;
import shop.lotfresh.paymentservice.domain.refund.repository.RefundRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RefundService {
    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public Long createRefund(Long orderDetailId, RefundCreateRequest request) {
        boolean refundExists = refundRepository.existsByOrderDetailId(orderDetailId);
        if (refundExists) {
            throw new IllegalArgumentException("A refund already exists for this order detail: " + orderDetailId);
        }

        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + request.getOrderId()));
        Refund refund = request.toEntity(orderDetailId, payment);
        Refund createdRefund = refundRepository.save(refund);
        return createdRefund.getId();
    }
    
}
