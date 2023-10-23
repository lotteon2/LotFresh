package shop.lotfresh.paymentservice.domain.refund.entity;

import lombok.*;
import shop.lotfresh.paymentservice.common.BaseTimeEntity;
import shop.lotfresh.paymentservice.domain.payment.entity.Payment;
import shop.lotfresh.paymentservice.domain.refund.vo.KakaopayRefundVO;

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
    private Long stock;

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
        if (this.status != RefundStatus.READY) {
            throw new IllegalStateException("Only refund in READY status can be approved");
        }
        this.status = RefundStatus.APPROVED;
    }

    public void rejectRefund() {
        if (this.status != RefundStatus.READY) {
            throw new IllegalStateException("Only refund in READY status can be rejected");
        }
        this.status = RefundStatus.REJECTED;
    }

    public KakaopayRefundVO toVO(String kakaopayCid) {
        return KakaopayRefundVO.builder()
                .cid(kakaopayCid)
                .tid(payment.getTid())
                .cancelAmount(amount)
                .cancelTaxFreeAmount(0L)
                .build();

    }

}
