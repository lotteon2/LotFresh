package com.lotfresh.orderservice.domain.order;

import com.lotfresh.orderservice.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authId;

    private Boolean isDeleted = false;
    @Builder
    private Order(Long authId) {
        this.authId = authId;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
