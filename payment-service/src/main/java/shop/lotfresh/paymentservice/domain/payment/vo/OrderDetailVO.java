package shop.lotfresh.paymentservice.domain.payment.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Builder
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
