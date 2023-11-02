package shop.lotfresh.paymentservice.domain.payment.vo;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {
    @NotEmpty(message = "productName cannot be Empty")
    private String productName;

    @Min(value = 0, message = "Price must be equal or greater than 0")
    private Long originalPrice;

    @Min(value = 0, message = "Price must be equal or greater than 0")
    private Long discountedPrice;

    @Min(value = 1, message = "Quantity must be equal or greater than 1")
    private Long quantity;
}
