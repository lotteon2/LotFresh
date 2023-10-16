package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
@Transactional
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("회원 아이디를 입력받아 주문을 생성한다")
    @Test
    void createOrder() {
        // given
        Long authId = 1L;
        Order order = Order.builder()
                .authId(authId)
                .build();

        // when
        Order savedOrder = orderRepository.save(order); // jdbc에서 저장된 값을 가져오는 걸 할텐데 그때 아마 id값만 가져오는거 같다?

        em.clear();

        Order getOrder = orderRepository.findById(savedOrder.getId()).get();

        // then
        Assertions.assertThat(getOrder.getId()).isNotNull();
        Assertions.assertThat(getOrder.getAuthId()).isEqualTo(authId);
        Assertions.assertThat(getOrder.getIsDeleted()).isFalse();
        Assertions.assertThat(getOrder.getCreatedAt()).isNotNull();
        Assertions.assertThat(getOrder.getUpdatedAt()).isNotNull();

    }

    @DisplayName("특정 유저의 주문 정보를 pageable에서 요구한 만큼만 가져온다")
    @Test
    void getOrdersWithPaging() {
        // given
        Long authId = 1L;
        Order order1 = createOrder(authId);
        Order order2 = createOrder(authId);
        Order order3 = createOrder(authId);
        Order order4 = createOrder(authId);
        Order order5 = createOrder(authId);
        Order order6 = createOrder(authId);
        Order order7 = createOrder(authId);
        Order order8 = createOrder(authId);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        orderRepository.save(order4);
        orderRepository.save(order5);
        orderRepository.save(order6);
        orderRepository.save(order7);
        orderRepository.save(order8);

        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);

        // when
        Page<Order> ordersWithPaging = orderRepository.getOrdersWithPaging(authId, pageRequest);

        // then
        Assertions.assertThat(ordersWithPaging.getTotalPages()).isEqualTo(2);
        Assertions.assertThat(ordersWithPaging.getTotalElements()).isEqualTo(8);

        Assertions.assertThat(ordersWithPaging.getContent()).hasSize(size);
    }

    @DisplayName("페이징을 초과하는 page값을 요청하면 빈 배열이 반환된다")
    @Test
    void getOrdersWithPaging2() {
        // given
        Long authId = 1L;
        Order order1 = createOrder(authId);
        Order order2 = createOrder(authId);
        Order order3 = createOrder(authId);
        Order order4 = createOrder(authId);
        Order order5 = createOrder(authId);
        Order order6 = createOrder(authId);
        Order order7 = createOrder(authId);
        Order order8 = createOrder(authId);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        orderRepository.save(order4);
        orderRepository.save(order5);
        orderRepository.save(order6);
        orderRepository.save(order7);
        orderRepository.save(order8);

        int page = Integer.MAX_VALUE;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);

        // when
        Page<Order> ordersWithPaging = orderRepository.getOrdersWithPaging(authId, pageRequest);

        // then
        Assertions.assertThat(ordersWithPaging.getContent()).isEmpty();
    }


    private Order createOrder(Long userId) {
        return Order.builder()
                .authId(userId)
                .build();
    }

}