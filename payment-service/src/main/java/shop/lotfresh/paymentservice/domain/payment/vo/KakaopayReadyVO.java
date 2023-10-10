package shop.lotfresh.paymentservice.domain.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaopayReadyVO {
    private String cid;

    private String partnerOrderId;
    private String partnerUserId;

    private String itemName;
    private Long quantity;

    private Long totalAmount;
    private Long taxFreeAmount;

    private String approvalUrl;
    private String cancelUrl;
    private String failUrl;
}
