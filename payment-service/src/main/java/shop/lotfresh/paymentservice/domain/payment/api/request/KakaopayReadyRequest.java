package shop.lotfresh.paymentservice.domain.payment.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayReadyVO;
import shop.lotfresh.paymentservice.domain.payment.vo.OrderDetailVO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class KakaopayReadyRequest {
    @NotNull(message = "orderId cannot be null")
    private Long orderId;

    @NotNull(message = "isFromCart cannot be null")
    private Boolean isFromCart;

    private Boolean isBargain;

    @NotEmpty(message = "orderDetails cannot be empty")
    @Valid
    private List<OrderDetailVO> orderDetails;

    private String province;

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
