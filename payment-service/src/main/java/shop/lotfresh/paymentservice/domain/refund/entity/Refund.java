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

    // Service에 에러 다 모으는게 좋을지 고민이됨. 이거는 도메인에 넣는게 좋은지?
    public void approveRefund() {
        if (this.status != RefundStatus.READY) {
            throw new IllegalStateException("Only refund in READY status can be approved");
        }
        this.status = RefundStatus.APPROVED;
    }

    // Service에 에러 다 모으는게 좋을지 고민이됨. 이거는 도메인에 넣는게 좋은지?
    public void rejectRefund() {
        if (this.status != RefundStatus.READY) {
            throw new IllegalStateException("Only refund in READY status can be rejected");
        }
        this.status = RefundStatus.REJECTED;
    }

}
