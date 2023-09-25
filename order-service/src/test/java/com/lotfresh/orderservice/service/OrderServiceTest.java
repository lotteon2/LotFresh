package com.lotfresh.orderservice.service;

import com.lotfresh.orderservice.domain.Order;
import com.lotfresh.orderservice.domain.ProductOrder;
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


    @DisplayName("주문 정보를 입력 받아 주문 및 주문 상세내역들을 생성한다")
    @Test
    void insertOrder() {
        // given
        Long userId = 1L;
        List<ProductRequest> productRequests  = List.of(
            createProductRequest(1L, 100L, 1L),
            createProductRequest(2L, 500L, 2L),
            createProductRequest(3L, 1000L, 3L),
            createProductRequest(4L, 10000L, 4L)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .userId(userId)
                .productRequests(productRequests)
                .build();

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

    public static ProductRequest createProductRequest(Long productId,
                                                      Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

}