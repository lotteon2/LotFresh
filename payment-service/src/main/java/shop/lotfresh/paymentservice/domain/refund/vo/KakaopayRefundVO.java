package shop.lotfresh.paymentservice.domain.refund.vo;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class KakaopayRefundVO {
    private String cid;
    private String tid;
    private Integer cancelAmount;
    private Integer cancelTaxFreeAmount;


    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("cid", cid);
        map.add("tid", tid);
        map.add("cancel_amount", cancelAmount.toString());
        map.add("cancel_tax_free_amount", cancelTaxFreeAmount.toString());
        return map;
    }

}
