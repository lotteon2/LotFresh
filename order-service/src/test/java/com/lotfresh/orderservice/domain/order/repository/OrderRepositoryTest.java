package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("회원 아이디를 입력받아 주문을 생성한다")
    @Test
    void createOrder(){
        // given
        Long authId = 1L;
        Order order = Order.builder()
                .authId(authId)
                .build();

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        Assertions.assertThat(savedOrder.getId()).isNotNull();
        Assertions.assertThat(savedOrder.getAuthId()).isEqualTo(authId);
        Assertions.assertThat(savedOrder.getIsDeleted()).isFalse();
        Assertions.assertThat(savedOrder.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedOrder.getUpdatedAt()).isNotNull();

    }

}