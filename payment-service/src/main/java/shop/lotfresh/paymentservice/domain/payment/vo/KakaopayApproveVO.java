package shop.lotfresh.paymentservice.domain.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaopayApproveVO {
    private String cid;
    private String tid;
    private String partnerOrderId;
    private String partnerUserId;
    private String pgToken;
}
