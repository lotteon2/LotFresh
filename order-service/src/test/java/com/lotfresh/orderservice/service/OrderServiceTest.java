package com.lotfresh.orderservice.service;

import com.lotfresh.orderservice.domain.order.Order;
import com.lotfresh.orderservice.domain.productOrder.ProductOrder;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderStatus;
import com.lotfresh.orderservice.dto.request.OrderChangeStatusRequest;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.ProductRequest;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.repository.OrderRepository;
import com.lotfresh.orderservice.repository.ProductOrderRepository;
import com.lotfresh.orderservice.service.order.OrderService;
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
        OrderChangeStatusRequest orderChangeStatusRequest = OrderChangeStatusRequest.builder()
                .productOrderId(productOrderId)
                .productOrderStatus(requestedStatus)
                .build();

        // when
        orderService.changeProductOrderStatus(orderChangeStatusRequest);

        em.flush();
        em.clear();

        ProductOrder productOrder1 = productOrderRepository.findById(productOrderId).get();

        // then
        Assertions.assertThat(productOrder1.getStatus()).isEqualTo(requestedStatus);
    }

    @DisplayName("존재하지 않는 Id로 상태변경을 요청하면 데이터가 존재하지 않는다는 에러가 발생한다")
    @Test
    void changeProductOrderStatusWithNonExistentId() {
        // given
        ProductOrderId productOrderId = ProductOrderId.builder()
                .productId(1L)
                .build();

        ProductOrderStatus requestedStatus = ProductOrderStatus.CONFIRMED;
        OrderChangeStatusRequest orderChangeStatusRequest = OrderChangeStatusRequest.builder()
                .productOrderId(productOrderId)
                .productOrderStatus(requestedStatus)
                .build();

        // when // then
        Assertions.assertThatThrownBy(() -> orderService.changeProductOrderStatus(orderChangeStatusRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage("데이터가 존재하지 않습니다");

    }

    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

}