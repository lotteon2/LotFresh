package shop.lotfresh.paymentservice.domain.payment.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class KakaopayApproveErrorResponseVO {
    private Integer code;

    @JsonProperty(value = "msg")
    private String message;

    @JsonProperty(value = "extras")
    Map<String, String> extras;
}
