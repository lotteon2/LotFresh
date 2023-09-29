package com.lotfresh.orderservice.domain.productOrder;

import com.lotfresh.orderservice.domain.order.Order;
import com.lotfresh.orderservice.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductOrderTest {

    @DisplayName("상품주문은 CREATED상태가 아니면 상태 변경이 불가능하다")
    @ParameterizedTest
    @EnumSource(value=ProductOrderStatus.class, names = {"CREATED"},mode = EnumSource.Mode.EXCLUDE)
    void cannotChangeProductOrderStatus(ProductOrderStatus status) {
        // given
        ProductOrderId productOrderId = ProductOrderId.builder()
                .productId(1L)
                .build();
        Order order = Order.builder()
                .authId(1L)
                .build();
        ProductOrder productOrder = ProductOrder.builder()
                .id(productOrderId)
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(status)
                .build();
        ProductOrderStatus requestedStatus = ProductOrderStatus.CONFIRMED;

        // when // then
        Assertions.assertThatThrownBy(() -> productOrder.changeProductOrderStatus(requestedStatus))
                .isInstanceOf(CustomException.class)
                .hasMessage("주문 상태를 변경할 수 없습니다");

   }
}