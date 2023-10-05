package shop.lotfresh.paymentservice.domain.refund.entity;

import lombok.*;
import shop.lotfresh.paymentservice.common.BaseTimeEntity;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
        name="refund",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"productId", "orderId"})
)
public class Refund extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String refundReason;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String refundMethod;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    // TODO: Payment와 양방향 매핑은 필요해지면 작성
}
