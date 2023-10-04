package shop.lotfresh.paymentservice.domain.payment.entity;

import shop.lotfresh.paymentservice.common.BaseTimeEntity;
import shop.lotfresh.paymentservice.domain.order.entity.Order;

import javax.persistence.*;

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

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="orderId")
    private Order order;
    // TODO: 회원테이블의 oauthid를 ordertable에서 FK로 가져오는데, 나는 MSA라서 member table까지는 안가지고 있을것임. 어떻게할까?
    // order table에서 oauthid는 그냥 unique로 둬야하는가? (원래는 member_role table에서 FK로 가져옴)
    //

}
