package com.lotfresh.orderservice.domain.order.entity;

import com.lotfresh.orderservice.common.BaseEntity;
import com.lotfresh.orderservice.domain.order.entity.status.DeliveryStatus;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.entity.status.PaymentStatus;
import com.lotfresh.orderservice.domain.order.entity.status.RefundStatus;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
    private Long productId;

    @NotNull
    private Long price;

    @NotNull
    private Long quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderDetailStatus status;

    @NotNull
    private Boolean isDeleted = false;

    @NotNull
    private String productName;
    @NotNull
    private String productThumbnail;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus = DeliveryStatus.READY;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.READY;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus = RefundStatus.READY;

    @Builder
    private OrderDetail(Order order, Long productId, Long price, Long quantity, OrderDetailStatus status,
            String productName, String productThumbnail) {
        // 엔티티가 아닌 순수 객체 상태에서도 양방향 연관관계를 구현
        order.getOrderDetails().add(this);
        this.order = order;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.productName = productName;
        this.productThumbnail = productThumbnail;
    }

    public void changeProductOrderStatus(OrderDetailStatus status) {
        this.status = status;
    }
    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changeProductThumbnail(String productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    public void changeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void changePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void changeRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }


    public String getFinalStatusAsString() {
        if(status == OrderDetailStatus.CANCELED) return refundStatus.getMessage();
        else {
            if(paymentStatus == PaymentStatus.SUCCESS) return deliveryStatus.getMessage();
            return paymentStatus.getMessage();
        }
    }


    public void softDelete() {
        this.isDeleted = true;
    }

}
