package com.lotfresh.orderservice.domain.order.entity;

import com.lotfresh.orderservice.common.BaseEntity;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(unique = true,
            nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderDetailStatus status;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Builder
    private OrderDetail(Order order, Long productId, Long price, Long quantity, OrderDetailStatus status) {
        this.order = order;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public void changeProductOrderStatus(OrderDetailStatus status){
        if(this.status.isNotModifiable()){
            throw new CustomException(ErrorCode.STATUS_NOT_CHANGEABLE);
        }
        this.status = status;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
