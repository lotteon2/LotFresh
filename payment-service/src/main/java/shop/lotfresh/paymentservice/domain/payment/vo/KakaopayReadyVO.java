package shop.lotfresh.paymentservice.domain.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    public MultiValueMap<String, String> toMultiValueMap(Long orderId, Boolean isFromCart, String province) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("cid", cid);
        map.add("partner_order_id", partnerOrderId);
        map.add("partner_user_id", partnerUserId);
        map.add("item_name", itemName);
        map.add("quantity", quantity.toString());
        map.add("total_amount", totalAmount.toString());
        map.add("tax_free_amount", taxFreeAmount.toString());
        map.add("approval_url", approvalUrl +"/"+orderId+"/"+isFromCart + "/" + province);
        map.add("cancel_url", cancelUrl);
        map.add("fail_url", failUrl);

        return map;
    }
}