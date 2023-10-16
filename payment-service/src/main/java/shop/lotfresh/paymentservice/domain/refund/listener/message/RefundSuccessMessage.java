package shop.lotfresh.paymentservice.domain.refund.listener.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundSuccessMessage {
    @Builder.Default
    private String status = "REFUNDED";
}
