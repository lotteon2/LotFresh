package shop.lotfresh.paymentservice.domain.payment.api.request;

import lombok.Getter;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.payment.vo.KakaopayApproveVO;

import javax.validation.constraints.NotNull;

@Getter
public class KakaopayApproveRequest {
    @NotNull(message = "orderId cannot be null")
    private Long orderId;

    @NotNull(message = "pgToken cannot be null")
    private Long pgToken;

    public KakaopayApproveVO toKakaopayApproveVO(String cid, Payment payment) {
        return KakaopayApproveVO.builder()
                .pgToken(pgToken.toString())
                .cid(cid)
                .tid(payment.getTid())
                .partnerUserId(payment.getOauthId().toString())
                .partnerOrderId(payment.getOrderId().toString())
                .build();
    }
}
