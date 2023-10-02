package com.lotfresh.orderservice.service.order;

import com.lotfresh.orderservice.domain.order.Order;
import com.lotfresh.orderservice.domain.productOrder.ProductOrder;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderStatus;
import com.lotfresh.orderservice.dto.request.OrderChangeStatusRequest;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.OrderRefundRequest;
import com.lotfresh.orderservice.dto.request.ProductRequest;
import com.lotfresh.orderservice.dto.response.OrderCreateResponse;
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

        // when
        orderService.insertOrder(userId,productRequests);

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

    @DisplayName("요청받은 주문들의 isDeleted상태가 true로 변경되며, 이는 주문이 취소된 것으로 간주한다")
    @Test
    void revertInsertOrder() {
        // given
        ProductOrderId productOrderId1 = ProductOrderId.builder()
                .productId(1L)
                .build();
        ProductOrderId productOrderId2 = ProductOrderId.builder()
                .productId(2L)
                .build();
        Order order = Order.builder()
                .authId(1L)
                .build();
        ProductOrder productOrder1 = ProductOrder.builder()
                .id(productOrderId1)
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(ProductOrderStatus.CREATED)
                .build();
        ProductOrder productOrder2 = ProductOrder.builder()
                .id(productOrderId2)
                .order(order)
                .price(1000L)
                .quantity(1L)
                .status(ProductOrderStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);
        productOrderRepository.save(productOrder1);
        productOrderRepository.save(productOrder2);

        OrderCreateResponse orderCreateResponse =
                OrderCreateResponse.builder()
                        .orderId(savedOrder.getId())
                        .productIds(List.of(productOrderId1,productOrderId2))
                        .build();

        // when
        orderService.revertInsertOrder(orderCreateResponse);
        Order revertedOrder = orderRepository.findById(savedOrder.getId()).get();
        ProductOrder revertedProductOrder1 = productOrderRepository
                .findById(productOrderId1).get();
        ProductOrder revertedProductOrder2 = productOrderRepository
                .findById(productOrderId2).get();

        // then
        Assertions.assertThat(revertedOrder.getIsDeleted()).isTrue();
        Assertions.assertThat(revertedProductOrder1.getIsDeleted()).isTrue();
        Assertions.assertThat(revertedProductOrder2.getIsDeleted()).isTrue();

    }

    @DisplayName("주문취소/환불 요청 시 해당 주문의 상태가 CANCELED로 변경된다")
    @Test
    void refundOrder() {
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

        // when
        orderService.refundOrder(productOrderId);

        em.flush();
        em.clear();

        ProductOrder productOrder1 = productOrderRepository.findById(productOrderId).get();

        // then
        Assertions.assertThat(productOrder1.getStatus()).isEqualTo(ProductOrderStatus.CANCELED);
    }


    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

}