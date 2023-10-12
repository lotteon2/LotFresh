package shop.lotfresh.paymentservice.domain.refund.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.repository.PaymentRepository;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;
import shop.lotfresh.paymentservice.domain.refund.repository.RefundRepository;
import shop.lotfresh.paymentservice.domain.refund.vo.KakaopayRefundVO;
import shop.lotfresh.paymentservice.domain.refund.webclient.KakaopayRefundApiClient;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RefundService {
    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final KakaopayRefundApiClient kakaopayRefundApiClient;

    @Value("${kakaopay.cid}")
    private String kakaopayCid;

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

    // TODO: NOTFOUND ERROR 모아서 CUSTOM ERROR 상속하는 클래스로 묶기
    @Transactional
    public void approveRefund(Long refundId) {
        Refund refund =
                refundRepository.findById(refundId).orElseThrow(NoSuchElementException::new);

        // payment 통해 찾은 refund들의 기존 환불성공금액들 + 이번 환불요청 금액이 결제 금액을 넘지않는지?(Approved만 합쳐야함)
        Long totalRefundedAmount = refundRepository.findTotalRefundedAmountByPaymentId(refund.getPayment().getId());
        totalRefundedAmount = (totalRefundedAmount == null) ? 0 : totalRefundedAmount;
        if (refund.getAmount() + totalRefundedAmount > refund.getPayment().getTransactionAmount()) {
            throw new IllegalArgumentException("The requested refund exceeds possible transaction amount");
        }

        KakaopayRefundVO request = refund.toVO(kakaopayCid);
        try {
            // 카카오페이 명세에 맞게 작성하였으나, 들어오는 객체의 값이 현재 우리 DB에 반영되는 상황은 아니라서 메소드 호출만 했음.
            kakaopayRefundApiClient.kakaopayRefund(request);

            // TODO: Kafka 통해 던지기 주문서버에 환불을 했으니 상태변경하라는 메세지 보내기. 😀
            // 이거 받고 주문서버에서 적절한 상품인 경우 재고 업데이트 하라고 해야되나? 내가 도와줄 수 있는 부분이 있나?
            refund.approveRefund();
        } catch (RuntimeException e) {
            log.error("Failed to refund KakaoPay: " + e.getMessage());
            // 통신하던 도중에 오류로 환불이 안된거니까 내 Data의 상태는 변경하지 않았다.
            throw e;
        }
    }

    // TODO: NOTFOUND ERROR 모아서 CUSTOM ERROR 상속하는 클래스로 묶기
    @Transactional
    public void rejectRefund(Long refundId) {
        Refund refund =
                refundRepository.findById(refundId).orElseThrow(NoSuchElementException::new);
        refund.rejectRefund();
    }
}
