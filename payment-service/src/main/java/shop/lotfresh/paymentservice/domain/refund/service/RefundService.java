package shop.lotfresh.paymentservice.domain.refund.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.repository.PaymentRepository;
import shop.lotfresh.paymentservice.domain.refund.api.request.RefundCreateRequest;
import shop.lotfresh.paymentservice.domain.refund.dto.RefundInfoListDTO;
import shop.lotfresh.paymentservice.domain.refund.dto.RefundInfoResponseDTO;
import shop.lotfresh.paymentservice.domain.refund.entity.Refund;
import shop.lotfresh.paymentservice.domain.refund.entity.RefundStatus;
import shop.lotfresh.paymentservice.domain.refund.listener.message.RefundSuccessMessage;
import shop.lotfresh.paymentservice.domain.refund.repository.RefundRepository;
import shop.lotfresh.paymentservice.domain.refund.vo.KakaopayRefundVO;
import shop.lotfresh.paymentservice.webclient.KakaopayApiClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RefundService {
    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final KakaopayApiClient kakaopayApiClient;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Value("${kakaopay.cid}")
    private String kakaopayCid;

    @Transactional
    public Long createRefund(Long orderDetailId, RefundCreateRequest request) {
        boolean refundExists = refundRepository.existsByOrderDetailId(orderDetailId); // 중복요청 방지
        if (refundExists) {
            throw new IllegalArgumentException("A refund already exists for this order detail: " + orderDetailId);
        }

        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + request.getOrderId()));

        // TODO: 해당 payment가 내꺼인지 확인.

        Refund refund = request.toEntity(orderDetailId, payment);
        Refund createdRefund = refundRepository.save(refund);
        return createdRefund.getId();
    }

    // TODO: NOTFOUND ERROR 모아서 CUSTOM ERROR 상속하는 클래스로 묶기
    @Transactional // FIXME: 동시요청 막기 위해서 격리수준 조정 필요. Entity에 Version 두는건 너무 과함.
    public void approveRefund(Long refundId) {
        Refund refund =
                refundRepository.findById(refundId).orElseThrow(NoSuchElementException::new);
        if (refund.getStatus() != RefundStatus.READY) {
            throw new IllegalStateException("The refund request is not in a READY state"); // 1차로 중복요청방지
        }

        // payment 통해 찾은 refund들의 기존 환불성공금액들 + 이번 환불요청 금액이 결제 금액을 넘지않는지?(Approved만 합친다)
        Long totalRefundedAmount = refundRepository.findTotalApprovedRefundedAmountByPaymentId(refund.getPayment().getId());
        totalRefundedAmount = (totalRefundedAmount == null) ? 0 : totalRefundedAmount;
        if (refund.getAmount() + totalRefundedAmount > refund.getPayment().getTransactionAmount()) {
            throw new IllegalArgumentException("The requested refund exceeds possible transaction amount");
        }

        KakaopayRefundVO request = refund.toVO(kakaopayCid);
        try {
            kakaopayApiClient.kakaopayRefund(request);

            // Kafka 통해 던지기. 주문서버에 환불을 했으니 상태변경하라는 메세지 보내기.
            RefundSuccessMessage message = RefundSuccessMessage.builder().build();
            kafkaTemplate.send("refund-success", String.valueOf(refund.getOrderDetailId()), message);
            refund.approveRefund();
        } catch (RuntimeException e) {
            log.error("Failed to refund KakaoPay: " + e.getMessage());
            /*
             통신하던 도중에 오류로 환불이 안된거니까 내 Data의 상태는 변경하지 않았다.
             여기서 에러나면 READY 상태인 데이터가 그대로 쌓이게 됨. 백엔드에서 카카오서버에 다시 요청하되,
             TODO: 재요청시에도 안되면 관리자가 클라이언트에서 다시 승인해주는걸로하자.
             */
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

    public RefundInfoResponseDTO getRefundDetailByRefundId(Long refundId) {
        Refund refund =
                refundRepository.findById(refundId).orElseThrow(NoSuchElementException::new);

        return RefundInfoResponseDTO.builder()
                .refundCreatedAt(refund.getCreatedAt())
                .refundId(refund.getId())
                .refundUpdatedAt(refund.getUpdatedAt())
                .productAmount(refund.getAmount()) // 우리 환불할때 배송비같은 차감이 없음. 개당금액 아니라 총금액임.
                .stock(refund.getStock())
                .refundMethod(refund.getRefundMethod())
                .refundedAmount(refund.getAmount())
                .build();
    }

    public RefundInfoListDTO getReadyRefunds(Long page, Long size) {
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        Page<Refund> refunds = refundRepository.findByStatus(RefundStatus.READY, pageable);

        List<RefundInfoResponseDTO> refundInfoList = refunds.stream()
                .map(refund -> RefundInfoResponseDTO.builder()
                        .refundCreatedAt(refund.getCreatedAt())
                        .refundId(refund.getId())
                        .refundUpdatedAt(refund.getUpdatedAt())
                        .stock(refund.getStock())
                        .productAmount(refund.getAmount())
                        .refundMethod(refund.getRefundMethod())
                        .build())
                .collect(Collectors.toList());

//        return RefundInfoListDTO.builder()
//                .refundInfoList(refundInfoList)
//                .build();
        return RefundInfoListDTO.of(refunds, refundInfoList);
    }
}
