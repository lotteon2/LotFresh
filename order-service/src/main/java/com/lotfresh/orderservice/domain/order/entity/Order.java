package com.lotfresh.orderservice.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotfresh.orderservice.common.BaseEntity;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @NotNull
    private Long authId;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    private String zipcode;
    private String roadAddress;
    private String detailAddress;
    @Builder
    private Order(Long authId, Address address) {
        this.authId = authId;
        this.zipcode = address.getZipcode();
        this.roadAddress = address.getRoadAddress();
        this.detailAddress = address.getDetailAddress();
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
