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

    @Column(nullable = false)
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

    private String productName;
    private String productThumbnail;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus = DeliveryStatus.READY;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.READY;
    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus = RefundStatus.READY;

    @Builder
    private OrderDetail(Order order, Long productId, Long price, Long quantity, OrderDetailStatus status) {
        this.order = order;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
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
