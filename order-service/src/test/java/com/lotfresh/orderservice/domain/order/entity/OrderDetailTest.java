package com.lotfresh.orderservice.domain.order.entity;

import com.lotfresh.orderservice.domain.order.entity.status.DeliveryStatus;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.entity.status.PaymentStatus;
import com.lotfresh.orderservice.domain.order.entity.status.RefundStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderDetailTest {

    @DisplayName("주문이 취소됐으면 환불에 대한 상태를 보여준다")
    @Test
    void giveRefundStatusWhenOrderIsCanceled() {
        // given
        OrderDetailStatus orderDetailStatus = OrderDetailStatus.CANCELED;
        RefundStatus refundStatus = RefundStatus.APPROVED;
        PaymentStatus paymentStatus = null;
        DeliveryStatus deliveryStatus = null;
        OrderDetail orderDetail = createOrderDetail(orderDetailStatus, paymentStatus, deliveryStatus, refundStatus);

        // when
        String result = orderDetail.getFinalStatusAsString();

        // then
        Assertions.assertThat(result).isEqualTo(refundStatus.getMessage());
    }
    @DisplayName("주문이 완료됐고 결제가 성공했으면 배송 상태를 보여준다")
    @Test
    void giveDeliveryStatusWhenOrderConfirmedAndPaymentSuccessed() {
        // given
        OrderDetailStatus orderDetailStatus = OrderDetailStatus.CONFIRMED;
        PaymentStatus paymentStatus = PaymentStatus.SUCCESS;
        DeliveryStatus deliveryStatus = DeliveryStatus.COMPLETE;
        RefundStatus refundStatus = null;
        OrderDetail orderDetail = createOrderDetail(orderDetailStatus, paymentStatus, deliveryStatus, refundStatus);
        // when
        String result = orderDetail.getFinalStatusAsString();

        // then
        Assertions.assertThat(result).isEqualTo(deliveryStatus.getMessage());

    }
    @DisplayName("주문이 완료됐지만 결제가 성공하지 않았으면 결제 상태를 보여준다")
    @ParameterizedTest
    @EnumSource(value = PaymentStatus.class, names = {"READY","CANCELED","FAILED"}, mode = EnumSource.Mode.INCLUDE)
    void givePaymentStatusWhenOrderConfirmedButPaymentNotSuccessed(PaymentStatus paymentStatus) {
        // given
        OrderDetailStatus orderDetailStatus = OrderDetailStatus.CONFIRMED;
        DeliveryStatus deliveryStatus = null;
        RefundStatus refundStatus = null;
        OrderDetail orderDetail = createOrderDetail(orderDetailStatus, paymentStatus, deliveryStatus, refundStatus);

        // when
        String result = orderDetail.getFinalStatusAsString();

        // then
        Assertions.assertThat(result).isEqualTo(paymentStatus.getMessage());

    }

    private OrderDetail createOrderDetail(OrderDetailStatus orderDetailStatus, PaymentStatus paymentStatus,
                                          DeliveryStatus deliveryStatus, RefundStatus refundStatus) {
        Order order = Order.builder()
                .authId(1L)
                .build();
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .productId(1L)
                .price(2L)
                .stock(3L)
                .status(orderDetailStatus)
                .build();
        orderDetail.changePaymentStatus(paymentStatus);
        orderDetail.changeDeliveryStatus(deliveryStatus);
        orderDetail.changeRefundStatus(refundStatus);
        return orderDetail;
    }

}