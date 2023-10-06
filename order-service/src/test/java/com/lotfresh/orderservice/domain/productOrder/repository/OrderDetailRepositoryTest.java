package com.lotfresh.orderservice.domain.productOrder.repository;

import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("ProductOrderId의 orderId는 MapsId에 의해 ProductOrder를 생성할 때 사용된 Order의 id와 같다")
    @Test
    void createProductOrder(){
        // given
        Order savedOrder = orderRepository.save(Order.builder()
                .authId(1L)
                .build());

        OrderDetail orderDetail = makeOrderDetail(savedOrder);

        // when
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        // then
        Assertions.assertThat(savedOrderDetail.getOrder().getId())
                .isEqualTo(savedOrder.getId());
    }

    private static OrderDetail makeOrderDetail(Order order){
        return OrderDetail.builder()
                .order(order)
                .price(1000L)
                .quantity(2L)
                .status(OrderDetailStatus.CREATED)
                .build();
    }

}