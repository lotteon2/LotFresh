package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class OrderDetailRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @DisplayName("주문 아이디로 주문 상세 정보들을 조회한다")
    @Test
    void findOrderDetailsByOrderId() {
        // given
        Order order = Order.builder()
                .authId(1L)
                .build();
        OrderDetail orderDetail1 = OrderDetail.builder()
                .order(order)
                .productId(1L)
                .price(10L)
                .quantity(100L)
                .status(OrderDetailStatus.CONFIRMED)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .order(order)
                .productId(2L)
                .price(20L)
                .quantity(200L)
                .status(OrderDetailStatus.CONFIRMED)
                .build();

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);

        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(savedOrder.getId());
        Assertions.assertThat(orderDetails).hasSize(2)
                .extracting("productId","price","quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(1L,10L,100L),
                        Tuple.tuple(2L,20L,200L)
                );

    }


}