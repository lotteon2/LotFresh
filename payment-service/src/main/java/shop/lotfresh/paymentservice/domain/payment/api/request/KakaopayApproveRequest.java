package shop.lotfresh.paymentservice.domain.payment.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayApproveVO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class KakaopayApproveRequest {
    @NotNull(message = "orderId cannot be null")
    private Long orderId;

    @NotEmpty(message = "pgToken cannot be Empty")
    private String pgToken;

    public KakaopayApproveVO toKakaopayApproveVO(String cid, Payment payment) {
        return KakaopayApproveVO.builder()
                .pgToken(pgToken)
                .cid(cid)
                .tid(payment.getTid())
                .partnerUserId(payment.getUserId().toString())
                .partnerOrderId(payment.getOrderId().toString())
                .build();
    }
}
