package shop.lotfresh.paymentservice.domain.payment.entity;

import lombok.*;
import shop.lotfresh.paymentservice.common.BaseTimeEntity;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentMethod;

    @Column
    private String pgToken;

    @Column(nullable = false)
    private String tid;

    @Column(nullable = false)
    private Long originalAmount;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String status;


    // order table을 가지고 있지 않음. Relation Mapping을 사용하지 않음.
    @Column(unique = true,
            nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long oauthId;

}
