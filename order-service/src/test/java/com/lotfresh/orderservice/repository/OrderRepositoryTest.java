package com.lotfresh.orderservice.repository;

import com.lotfresh.orderservice.domain.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        LocalDateTime timeBeforeCreation = LocalDateTime.now();
        Order order = Order.builder()
                .authId(authId)
                .build();

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        Assertions.assertThat(savedOrder.getId()).isNotNull();
        Assertions.assertThat(savedOrder.getAuthId()).isEqualTo(authId);
        Assertions.assertThat(savedOrder.getCreatedAt()).isAfter(timeBeforeCreation);
        Assertions.assertThat(savedOrder.getUpdatedAt()).isAfter(timeBeforeCreation);

    }

}