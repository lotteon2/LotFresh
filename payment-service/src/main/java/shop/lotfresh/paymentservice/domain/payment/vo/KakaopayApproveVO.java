package shop.lotfresh.paymentservice.domain.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaopayApproveVO {
    private String cid;
    private String tid;
    private String partnerOrderId;
    private String partnerUserId;
    private String pgToken;


    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("cid", cid);
        map.add("tid", tid);
        map.add("partner_order_id", partnerOrderId);
        map.add("partner_user_id", partnerUserId);
        map.add("pg_token", pgToken);

        return map;
    }
}
