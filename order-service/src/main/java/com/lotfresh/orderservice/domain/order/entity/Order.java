package com.lotfresh.orderservice.domain.order.entity;

import com.lotfresh.orderservice.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @Column(unique = true,
            nullable = false)
    private Long authId;

    @Column(nullable = false)
    private Boolean isDeleted = false;
    @Builder
    private Order(Long authId) {
        this.authId = authId;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
