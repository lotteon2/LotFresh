package shop.lotfresh.paymentservice.domain.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayApproveRequest;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayReadyRequest;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.entity.PaymentStatus;
import shop.lotfresh.paymentservice.domain.payment.repository.PaymentRepository;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayApproveVO;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayReadyVO;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayReadyResponseVO;
import shop.lotfresh.paymentservice.domain.payment.vo.OrderDetailVO;
import shop.lotfresh.paymentservice.webclient.KakaopayApiClient;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KakaopayApiClient kakaopayApiClient;

    @Value("${kakaopay.admin_key}")
    private String adminKey;

    @Value("${kakaopay.cid}")
    private String kakaopayCid;

    @Value("${kakaopay.approval_url}")
    private String approvalUrl;

    @Value("${kakaopay.fail_url}")
    private String failUrl;

    @Value("${kakaopay.cancel_url}")
    private String cancelUrl;

    @Transactional
    public String kakaopayReady(Long userId, KakaopayReadyRequest request) {
        Long orderId = request.getOrderId();

        List<OrderDetailVO> orderDetails = request.getOrderDetails();
        String itemName = generateItemName(orderDetails);
        Long totalQuantity = orderDetails.stream()
                .mapToLong(OrderDetailVO::getQuantity)
                .sum();

        Long totalPrice = orderDetails.stream()
                .mapToLong(order -> order.getPrice() * order.getQuantity())
                .sum();

        KakaopayReadyVO kakaopayReadyVO = request.toKakaopayReadyVO(userId,
                                                                    itemName,
                                                                    totalQuantity,
                                                                    totalPrice,
                                                                    kakaopayCid,
                                                                    approvalUrl,
                                                                    failUrl,
                                                                    cancelUrl);

        // queryParam에 orderId넘겨야 받을때 조립가능
        KakaopayReadyResponseVO kakaopayReadyResponseVO = kakaopayApiClient.kakaopayReady(orderId, kakaopayReadyVO);

        Payment payment = kakaopayReadyResponseVO.toEntity(userId, orderId, totalPrice);
        paymentRepository.save(payment); // TODO: 예외처리할게 있는지?

        //TODO: 큐알코드 화면 url만 넘겨줘도 괜찮을지 고민중.
        return kakaopayReadyResponseVO.getNextRedirectPcUrl();
    }

    @Transactional
    public void kakaopayApprove(KakaopayApproveRequest request) {
        Payment payment = paymentRepository.findByOrderId(request.getOrderId()).orElseThrow(NoSuchElementException::new);
        KakaopayApproveVO kakaopayApproveVO = request.toKakaopayApproveVO(kakaopayCid, payment);

        try {
            // 카카오페이 명세에 맞게 작성하였으나, 들어오는 객체의 값이 현재 우리 DB에 반영되는 상황은 아니라서 메소드 호출만 했음.
            kakaopayApiClient.kakaopayApprove(kakaopayApproveVO);
            payment.linkPaymentGateway(payment.getPgToken());
            payment.completePayment();
        } catch (RuntimeException e) {
            log.error("Failed to approve KakaoPay: " + e.getMessage());
            payment.failPayment();
            throw e;
        }
    }

    @Transactional
    public void abortPayment(Long orderId, PaymentStatus status) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("No such order: " + orderId));

        switch (status) {
            case CANCELED:
                payment.cancelPayment();
                break;
            case FAILED:
                payment.failPayment();
                break;
            default:
                throw new IllegalArgumentException("Invalid status for aborting a payment: " + status);
        }
    }



    public String generateItemName(List<OrderDetailVO> orderDetails) {
        String itemName;
        if (orderDetails.size() > 1) {
            itemName = orderDetails.get(0).getProductName() + "외 " + (orderDetails.size()-1) + "항목";
        } else {
            itemName = orderDetails.get(0).getProductName();
        }
        return itemName;
    }
}
