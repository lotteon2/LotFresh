package com.lotfresh.orderservice.domain.productOrder.domain;

import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.OrderDetailStatus;
import com.lotfresh.orderservice.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderDetailTest {

    @DisplayName("상품주문은 CREATED상태가 아니면 상태 변경이 불가능하다")
    @ParameterizedTest
    @EnumSource(value= OrderDetailStatus.class, names = {"CREATED"},mode = EnumSource.Mode.EXCLUDE)
    void cannotChangeProductOrderStatus(OrderDetailStatus status) {
        // given
        Order order = Order.builder()
                .authId(1L)
                .build();
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(status)
                .build();
        OrderDetailStatus requestedStatus = OrderDetailStatus.CONFIRMED;

        // when // then
        Assertions.assertThatThrownBy(() -> orderDetail.changeProductOrderStatus(requestedStatus))
                .isInstanceOf(CustomException.class)
                .hasMessage("주문 상태를 변경할 수 없습니다");

    }
}