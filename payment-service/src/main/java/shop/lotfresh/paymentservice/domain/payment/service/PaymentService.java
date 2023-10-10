package shop.lotfresh.paymentservice.domain.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayApproveRequest;
import shop.lotfresh.paymentservice.domain.payment.api.request.KakaopayReadyRequest;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.repository.PaymentRepository;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayApproveVO;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayReadyVO;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayResponseVO;
import shop.lotfresh.paymentservice.domain.payment.vo.OrderDetailVO;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

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

        /*
        TODO: feignclient 이용해서
         POST | https://kapi.kakao.com/v1/payment/ready?orderId=??
         받아오기 (queryParam에 orderId넘겨야)

        header에
        Authorization: KakaoAK ${SERVICE_APP_ADMIN_KEY}
        Content-type: application/x-www-form-urlencoded;charset=utf-8

        body에 kakaopayReadyVO
         */

        KakaopayResponseVO kakaopayResponseVO = null; // 해당요청에 대한 응답은 이렇게 온다.

        Payment payment = kakaopayResponseVO.toEntity(userId, orderId, totalPrice);
        paymentRepository.save(payment); // TODO: 예외처리할거 있는지 모르겠음.

        //TODO: 큐알코드 화면 url 넘겨주자, status code로 줄까? 아니면 따로 뭔가 메세지를 줄까?
        return kakaopayResponseVO.getNextRedirectPcUrl();
    }

    @Transactional
    public void kakaopayApprove(KakaopayApproveRequest request) {
        Payment payment = paymentRepository.findByOrderId(request.getOrderId()).orElseThrow(NoSuchElementException::new);
        KakaopayApproveVO kakaopayApproveVO = request.toKakaopayApproveVO(kakaopayCid, payment);
        /*
        TODO: feignclient 이용해서
         POST | https://kapi.kakao.com/v1/payment/approve?orderId=??

        header에
        Authorization: KakaoAK ${SERVICE_APP_ADMIN_KEY}
        Content-type: application/x-www-form-urlencoded;charset=utf-8

        body에 kakaopayApproveVO
         */


        // 오류 안나왔을시 200으로 돈 잘빠져나갔을시..
        payment.completePayment();



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
