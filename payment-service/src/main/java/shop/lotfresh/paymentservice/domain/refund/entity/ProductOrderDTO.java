package shop.lotfresh.paymentservice.domain.refund.entity;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderDTO {
    private Long productId;
    private Long orderId;
    private Long price;
}
