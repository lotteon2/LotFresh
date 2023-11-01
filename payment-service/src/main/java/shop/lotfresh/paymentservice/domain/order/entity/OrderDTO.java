package shop.lotfresh.paymentservice.domain.order.entity;

import lombok.*;
import shop.lotfresh.paymentservice.common.BaseTimeEntity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDTO extends BaseTimeEntity {

    private Long id;
    private Long userId;
    private Boolean isDeleted;

}
