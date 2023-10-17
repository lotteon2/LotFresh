package shop.lotfresh.paymentservice.domain.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInfoResponseDTO {
    private String paymentMethod;
    private Long originalAmount;
    private Long discountedAmount;
    private Long transactionAmount;

}
