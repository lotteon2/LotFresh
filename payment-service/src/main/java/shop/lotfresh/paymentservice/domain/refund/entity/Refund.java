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
public class Refund extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String refundReason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private RefundStatus status = RefundStatus.READY;

    @Column(nullable = false)
    private String refundMethod;

    @Column(nullable = false)
    private Long orderDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    // TODO: Payment와 양방향 매핑은 필요해지면 작성

    public void approveRefund() {
        this.status = RefundStatus.APPROVED;
    }

    public void rejectRefund() {
        this.status = RefundStatus.REJECTED;
    }

}
