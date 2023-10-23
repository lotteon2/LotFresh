package shop.lotfresh.paymentservice.domain.payment.listener.message;

import lombok.Data;

@Data
public class PaymentAbortMessage {
    private String status;
}
