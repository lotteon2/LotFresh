package com.lotfresh.orderservice.service;

import com.lotfresh.orderservice.domain.Order;
import com.lotfresh.orderservice.domain.ProductOrder;
import com.lotfresh.orderservice.domain.ProductOrderId;
import com.lotfresh.orderservice.domain.ProductOrderStatus;
import com.lotfresh.orderservice.dto.OrderChangeStatusRequest;
import com.lotfresh.orderservice.dto.OrderCreateRequest;
import com.lotfresh.orderservice.dto.ProductRequest;
import com.lotfresh.orderservice.repository.OrderRepository;
import com.lotfresh.orderservice.repository.ProductOrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private EntityManager em;


    @DisplayName("주문 정보를 입력 받아 주문 및 주문 상세내역들을 생성한다")
    @Test
    void insertOrder() {
        // given
        Long userId = 1L;
        List<ProductRequest> productRequests  = List.of(
                ProductRequest.forTest(1L, 100L, 1L),
                ProductRequest.forTest(2L, 500L, 2L),
                ProductRequest.forTest(3L, 1000L, 3L),
                ProductRequest.forTest(4L, 10000L, 4L)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.forTest(userId,productRequests);

        // when
        orderService.insertOrder(orderCreateRequest);

        // then
        List<Order> orders = orderRepository.findAll();
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        Assertions.assertThat(orders).hasSize(1);
        Assertions.assertThat(productOrders).hasSize(productRequests.size())
                .extracting("order")
                .containsOnly(orders.get(0));
    }

    @DisplayName("상품주문Id와 상태에 대한 정보를 받아 상품주문의 상태를 변경한다")
    @Test
    void changeProductOrderStatus() {
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
                .status(ProductOrderStatus.CREATED)
                .build();

        orderRepository.save(order);
        productOrderRepository.save(productOrder);

        ProductOrderStatus requestedStatus = ProductOrderStatus.CONFIRMED;
        OrderChangeStatusRequest orderChangeStatusRequest =
                OrderChangeStatusRequest.forTest(productOrderId, requestedStatus);

        // when
        orderService.changeProductOrderStatus(orderChangeStatusRequest);

        em.flush();
        em.clear();

        ProductOrder productOrder1 = productOrderRepository.findById(productOrderId).get();

        // then
        Assertions.assertThat(productOrder1.getStatus()).isEqualTo(requestedStatus);
    }


}