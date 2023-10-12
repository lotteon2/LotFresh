package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.service.response.BestProductsResponse;
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
        OrderDetail orderDetail1 = createOrderDetail(order,1L,10L,100L);

        OrderDetail orderDetail2 = createOrderDetail(order,2L,20L,200L);

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);

        // when
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(savedOrder.getId());

        // then
        Assertions.assertThat(orderDetails).hasSize(2)
                .extracting("productId","price","quantity")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(1L,10L,100L),
                        Tuple.tuple(2L,20L,200L)
                );

    }

    @DisplayName("가장 많이 구매된 상품 N개를 '판매순으로 정렬해' 반환한다")
    @Test
    void mostSoldProducts() {
        // given
        Order order = Order.builder()
                .authId(1L)
                .build();

        // 5번 상품 4개, 4번 상품 2개, 3번 상품 2개, 2번 상품 3개, 1번 상품 1개
        OrderDetail orderDetail1 = createOrderDetail(order,5L,1L,1L);
        OrderDetail orderDetail2 = createOrderDetail(order,5L,2L,2L);
        OrderDetail orderDetail3 = createOrderDetail(order,5L,3L,3L);
        OrderDetail orderDetail4 = createOrderDetail(order,5L,4L,4L);
        OrderDetail orderDetail5 = createOrderDetail(order,4L,1L,1L);
        OrderDetail orderDetail6 = createOrderDetail(order,4L,2L,2L);
        OrderDetail orderDetail7 = createOrderDetail(order,3L,3L,3L);
        OrderDetail orderDetail8 = createOrderDetail(order,3L,4L,4L);
        OrderDetail orderDetail9 = createOrderDetail(order,2L,1L,1L);
        OrderDetail orderDetail10 = createOrderDetail(order,2L,2L,2L);
        OrderDetail orderDetail11 = createOrderDetail(order,2L,3L,3L);
        OrderDetail orderDetail12 = createOrderDetail(order,1L,4L,4L);

        orderRepository.save(order);
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);
        orderDetailRepository.save(orderDetail3);
        orderDetailRepository.save(orderDetail4);
        orderDetailRepository.save(orderDetail5);
        orderDetailRepository.save(orderDetail6);
        orderDetailRepository.save(orderDetail7);
        orderDetailRepository.save(orderDetail8);
        orderDetailRepository.save(orderDetail9);
        orderDetailRepository.save(orderDetail10);
        orderDetailRepository.save(orderDetail11);
        orderDetailRepository.save(orderDetail12);

        int limitCnt = 3;

        // when
        List<BestProductsResponse> mostSoldProducts = orderDetailRepository.mostSoldProducts(limitCnt);

        // then
        Assertions.assertThat(mostSoldProducts).hasSize(limitCnt)
                .extracting("cnt")
                .containsExactly(4L,3L,2L);

    }

    private OrderDetail createOrderDetail(Order order, Long productId, Long price, Long quantity) {
        return OrderDetail.builder()
                .order(order)
                .productId(productId)
                .price(price)
                .quantity(quantity)
                .productName("제품명")
                .productThumbnail("제품썸네일")
                .status(OrderDetailStatus.CONFIRMED)
                .build();

    }


}