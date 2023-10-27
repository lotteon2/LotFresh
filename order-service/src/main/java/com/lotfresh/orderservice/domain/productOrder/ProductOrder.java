package com.lotfresh.orderservice.domain.productOrder;

import com.lotfresh.orderservice.domain.BaseEntity;
import com.lotfresh.orderservice.domain.order.Order;
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
public class ProductOrder extends BaseEntity {
    @EmbeddedId
    private ProductOrderId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long price;

    private Long quantity;

    @Enumerated(EnumType.STRING)
    private ProductOrderStatus status;

    public void changeProductOrderStatus(ProductOrderStatus status){
        if(this.status.isNotModifiable()){
            throw new CustomException(ErrorCode.STATUS_NOT_CHANGEABLE);
        }
        this.status = status;
    }

    @Builder
    private ProductOrder(ProductOrderId id, Order order, Long price, Long quantity, ProductOrderStatus status) {
        this.id = id;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }


}
