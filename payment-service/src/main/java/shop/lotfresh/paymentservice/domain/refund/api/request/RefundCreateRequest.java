package shop.lotfresh.paymentservice.domain.refund.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class RefundCreateRequest {
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

}
