package com.lotfresh.orderservice.domain.order.service;

import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.orchestrator.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import com.lotfresh.orderservice.exception.CustomException;
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
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private EntityManager em;


    @DisplayName("주문 정보를 입력 받아 주문 및 주문 상세내역들을 생성한다")
    @Test
    void insertOrder() {
        // given
        List<ProductRequest> productRequests  = List.of(
                createProductRequest(1L, 100L, 1L),
                createProductRequest(2L, 500L, 2L),
                createProductRequest(3L, 1000L, 3L),
                createProductRequest(4L, 10000L, 4L)
        );

        // when
        orderService.insertOrder(productRequests);

        // then
        List<Order> orders = orderRepository.findAll();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        Assertions.assertThat(orders).hasSize(1);
        Assertions.assertThat(orderDetails).hasSize(productRequests.size())
                .extracting("order")
                .containsOnly(orders.get(0));
    }

    @DisplayName("상품주문Id와 상태에 대한 정보를 받아 상품주문의 상태를 변경한다")
    @Test
    void changeProductOrderStatus() {
        // given
        Order order = Order.builder()
                .authId(1L)
                .build();
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(OrderDetailStatus.CREATED)
                .build();

        orderRepository.save(order);
        orderDetailRepository.save(orderDetail);

        OrderDetailStatus requestedStatus = OrderDetailStatus.CONFIRMED;
        OrderDetailChangeStatusRequest orderDetailChangeStatusRequest = OrderDetailChangeStatusRequest.builder()
                .orderDetailId(orderDetail.getId())
                .orderDetailStatus(requestedStatus)
                .build();

        // when
        orderService.changeProductOrderStatus(orderDetailChangeStatusRequest);

        em.flush();
        em.clear();

        OrderDetail orderDetail1 = orderDetailRepository.findById(orderDetail.getId()).get();

        // then
        Assertions.assertThat(orderDetail1.getStatus()).isEqualTo(requestedStatus);
    }

    @DisplayName("존재하지 않는 Id로 상태변경을 요청하면 데이터가 존재하지 않는다는 에러가 발생한다")
    @Test
    void changeProductOrderStatusWithNonExistentId() {
        // given
        Long orderDetailId = 1L;

        OrderDetailStatus requestedStatus = OrderDetailStatus.CONFIRMED;
        OrderDetailChangeStatusRequest orderDetailChangeStatusRequest = OrderDetailChangeStatusRequest.builder()
                .orderDetailId(orderDetailId)
                .orderDetailStatus(requestedStatus)
                .build();

        // when // then
        Assertions.assertThatThrownBy(() -> orderService.changeProductOrderStatus(orderDetailChangeStatusRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage("데이터가 존재하지 않습니다");

    }

    @DisplayName("요청받은 주문들의 isDeleted상태가 true로 변경되며, 이는 주문이 취소된 것으로 간주한다")
    @Test
    void revertInsertOrder() {
        // given

        Order order = Order.builder()
                .authId(1L)
                .build();
        OrderDetail orderDetail1 = OrderDetail.builder()
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(OrderDetailStatus.CREATED)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(OrderDetailStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);

        OrderCreateResponse orderCreateResponse =
                OrderCreateResponse.builder()
                        .order(savedOrder)
                        .orderDetails(List.of(orderDetail1,orderDetail2))
                        .build();

        // when
        orderService.revertInsertOrder(orderCreateResponse);
        Order revertedOrder = orderRepository.findById(savedOrder.getId()).get();
        OrderDetail revertedOrderDetail1 = orderDetailRepository
                .findById(orderDetail1.getId()).get();
        OrderDetail revertedOrderDetail2 = orderDetailRepository
                .findById(orderDetail2.getId()).get();

        // then
        Assertions.assertThat(revertedOrder.getIsDeleted()).isTrue();
        Assertions.assertThat(revertedOrderDetail1.getIsDeleted()).isTrue();
        Assertions.assertThat(revertedOrderDetail2.getIsDeleted()).isTrue();

    }

    @DisplayName("주문취소/환불 요청 시 해당 주문의 상태가 CANCELED로 변경된다")
    @Test
    void refundOrder() {
        // given
        Order order = Order.builder()
                .authId(1L)
                .build();
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(OrderDetailStatus.CREATED)
                .build();

        orderRepository.save(order);
        orderDetailRepository.save(orderDetail);

        // when
        orderService.refundOrder(orderDetail.getId());

        em.flush();
        em.clear();

        OrderDetail orderDetail1 = orderDetailRepository.findById(orderDetail.getId()).get();

        // then
        Assertions.assertThat(orderDetail1.getStatus()).isEqualTo(OrderDetailStatus.CANCELED);
    }


    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

}