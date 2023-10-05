package shop.lotfresh.paymentservice.domain.refund.entity;

import lombok.*;
import shop.lotfresh.paymentservice.common.BaseTimeEntity;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderDTO {
    private Long productId;
    private Long orderId;
    private Long price;
}
