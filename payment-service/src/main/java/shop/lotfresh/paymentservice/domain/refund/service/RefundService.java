package shop.lotfresh.paymentservice.domain.refund.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.repository.PaymentRepository;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;
import shop.lotfresh.paymentservice.domain.refund.repository.RefundRepository;

import java.util.NoSuchElementException;

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

    // TODO: NOTFOUND ERROR 모아서 클래스로 묶기
    @Transactional
    public void approveRefund(Long refundId) {
        Refund refund =
                refundRepository.findById(refundId).orElseThrow(NoSuchElementException::new);

        Long totalRefundedAmount = refundRepository.findTotalRefundedAmountByPaymentId(refund.getPayment().getId());
        totalRefundedAmount = (totalRefundedAmount == null) ? 0 : totalRefundedAmount;
        if (refund.getAmount() + totalRefundedAmount > refund.getPayment().getTransactionAmount()) {
            throw new IllegalArgumentException("The requested refund exceeds the transaction amount");
        }

        // TODO: 카카오페이 통해 환불요청하기.

        refund.approveRefund();
    }

    // TODO: NOTFOUND ERROR 모아서 클래스로 묶기
    @Transactional
    public void rejectRefund(Long refundId) {
        Refund refund =
                refundRepository.findById(refundId).orElseThrow(NoSuchElementException::new);
        refund.rejectRefund();
    }
}
