package shop.lotfresh.paymentservice.domain.payment.api.request;

import lombok.Getter;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayReadyVO;
import shop.lotfresh.paymentservice.domain.payment.vo.OrderDetailVO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class KakaopayReadyRequest {
    @NotNull(message = "orderId cannot be null")
    private Long orderId;

    @NotEmpty(message = "orderDetails cannot be empty")
    private List<OrderDetailVO> orderDetails;


    public KakaopayReadyVO toKakaopayReadyVO(Long userId,
                                             String itemName,
                                             Long totalQuantity,
                                             Long totalPrice,
                                             String kakaopayCid,
                                             String approvalUrl,
                                             String failUrl,
                                             String cancelUrl) {

        return KakaopayReadyVO.builder()
                .cid(kakaopayCid)
                .partnerOrderId(orderId.toString())
                .partnerUserId(userId.toString())
                .itemName(itemName)
                .quantity(totalQuantity)      // 총 상품개수. orderDetails 이용
                .totalAmount(totalPrice)      // 총 금액. orderDetails 이용
                .taxFreeAmount(0L)            // 면제 안팔고 있으니 0으로 고정
                .approvalUrl(approvalUrl)
                .failUrl(failUrl)
                .cancelUrl(cancelUrl)
                .build();
    }
}
