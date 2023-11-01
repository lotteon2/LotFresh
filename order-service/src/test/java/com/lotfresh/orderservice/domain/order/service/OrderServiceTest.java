package com.lotfresh.orderservice.domain.order.service;

import com.lotfresh.orderservice.domain.orchestrator.controller.request.Address;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.order.entity.status.RefundStatus;
import com.lotfresh.orderservice.domain.order.service.response.*;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import com.lotfresh.orderservice.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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


    @DisplayName("제품 아이디,가격,수량들을 입력 받아 주문 및 주문 상세내역들을 생성한다")
    @Test
    void insertOrder() {
        // given
        List<ProductRequest> productRequests  = List.of(
                createProductRequest(1L, 100L, 1L),
                createProductRequest(2L, 500L, 2L),
                createProductRequest(3L, 1000L, 3L),
                createProductRequest(4L, 10000L, 4L)
        );
        Address address = Address.builder()
                .zipcode("zipcode")
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();

        // when
        orderService.insertOrder(productRequests, address);

        // then
        List<Order> orders = orderRepository.findAll();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        Assertions.assertThat(orders).hasSize(1);
        Assertions.assertThat(orderDetails).hasSize(productRequests.size())
                .extracting("order")
                .containsOnly(orders.get(0));
    }

    @DisplayName("주문 상세내역Id와 상태에 대한 정보를 받아 주문 상세내역의 상태를 변경한다")
    @Test
    void changeProductOrderStatus() {
        // given
        Long userId = 1L;
        Order order = createOrder(userId);
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .productId(1L)
                .price(1000L)
                .stock(1L)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .status(OrderDetailStatus.CONFIRMED)
                .build();

        orderRepository.save(order);
        orderDetailRepository.save(orderDetail);

        OrderDetailStatus requestedStatus = OrderDetailStatus.CONFIRMED;

        // when
        orderService.changeProductOrderStatus(orderDetail.getId(),requestedStatus);

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
        Long nonExistOrderDetailId = 1L;

        OrderDetailStatus requestedStatus = OrderDetailStatus.CONFIRMED;

        // when // then
        Assertions.assertThatThrownBy(() -> orderService.changeProductOrderStatus(nonExistOrderDetailId,requestedStatus))
                .isInstanceOf(CustomException.class)
                .hasMessage("데이터가 존재하지 않습니다");

    }

    @DisplayName("요청받은 주문들의 isDeleted상태가 true로 변경되며, 이는 주문이 취소된 것으로 간주한다")
    @Test
    void revertInsertOrder() {
        // given
        Long userId = 1L;
        Order order = createOrder(userId);
        OrderDetail orderDetail1 = OrderDetail.builder()
                .order(order)
                .productId(1L)
                .price(1000L)
                .stock(1L)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .status(OrderDetailStatus.CONFIRMED)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .order(order)
                .productId(1L)
                .price(1000L)
                .stock(1L)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .status(OrderDetailStatus.CONFIRMED)
                .build();

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);

        // when
        orderService.revertInsertOrder(savedOrder.getId(), List.of(orderDetail1.getId(), orderDetail2.getId()));
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
        Long userId = 1L;
        Order order = createOrder(userId);
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .productId(1L)
                .price(1000L)
                .stock(1L)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .status(OrderDetailStatus.CONFIRMED)
                .build();

        orderRepository.save(order);
        orderDetailRepository.save(orderDetail);

        em.flush();
        em.clear();

        // when
        orderService.refundOrder(orderDetail.getId());

        em.flush();
        em.clear();

        OrderDetail changedOrderDetail = orderDetailRepository.findById(orderDetail.getId()).get();

        // then
        Assertions.assertThat(changedOrderDetail.getStatus()).isEqualTo(OrderDetailStatus.CANCELED);
    }

    @DisplayName("주문 아이디로 주문 상세 정보들을 조회한다")
    @Test
    void getOrderDetails() {
        // given
        Long userId = 1L;
        Order order = createOrder(userId);
        OrderDetail orderDetail1 = createOrderDetail(order,1L,10L,100L);
        OrderDetail orderDetail2 = createOrderDetail(order,2L,20L,200L);

        Order savedOrder = orderRepository.save(order);
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);

        // when
        OrderResponse orderResponse = orderService.getOrderResponse(savedOrder.getId());

        // then
        Assertions.assertThat(orderResponse.getOrderCreatedTime()).isEqualTo(savedOrder.getCreatedAt().toString());
        Assertions.assertThat(orderResponse.getOrderDetailResponses()).hasSize(2)
                .extracting("price","stock","productName","productThumbnail")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(10L,100L,"제품명","제품썸네일"),
                        Tuple.tuple(20L,200L,"제품명","제품썸네일")
                );

    }

    @DisplayName("가장 많이 구매된 상품 N개를 판매순으로 정렬해 반환한다")
    @Test
    void getMostSoldProducts() {
        // given
        Long userId = 1L;
        Order order = createOrder(userId);
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
        List<BestProductsResponse> mostSoldProducts = orderService.getMostSoldProducts(limitCnt);

        // then
        Assertions.assertThat(mostSoldProducts).hasSize(limitCnt)
                .extracting("cnt")
                .containsExactly(4L,3L,2L);

    }

    @DisplayName("베스트상품 조회는 아무런 구매 정보가 존재하지 않을 때 빈 배열을 반환한다")
    @Test
    void returnEmptyListWhenOrderDetailIsNotExist() {
        int limitCnt = 3;
        List<BestProductsResponse> mostSoldProducts = orderService.getMostSoldProducts(limitCnt);
        Assertions.assertThat(mostSoldProducts).isEmpty();

    }

    @DisplayName("특정 유저의 주문 및 주문상세 정보를 pageable해서 요구한 만큼만 가져온다")
    @Test
    void getOrdersWithPaging() {
        // given
        Long userId = 1L;
        Order order1 = createOrder(userId);
        Order order2 = createOrder(userId);
        Order order3 = createOrder(userId);
        Order order4 = createOrder(userId);
        Order order5 = createOrder(userId);
        Order order6 = createOrder(userId);
        Order order7 = createOrder(userId);
        Order order8 = createOrder(userId);

        OrderDetail orderDetail1 = createOrderDetail(order1,1L);
        OrderDetail orderDetail2 = createOrderDetail(order1,2L);
        OrderDetail orderDetail3 = createOrderDetail(order1,3L);

        OrderDetail orderDetail4 = createOrderDetail(order2,4L);
        OrderDetail orderDetail5 = createOrderDetail(order2,5L);
        OrderDetail orderDetail6 = createOrderDetail(order2,6L);

        OrderDetail orderDetail7 = createOrderDetail(order3,7L);
        OrderDetail orderDetail8 = createOrderDetail(order3,8L);
        OrderDetail orderDetail9 = createOrderDetail(order3,9L);

        OrderDetail orderDetail10 = createOrderDetail(order4,10L);
        OrderDetail orderDetail11 = createOrderDetail(order4,11L);
        OrderDetail orderDetail12 = createOrderDetail(order4,12L);

        OrderDetail orderDetail13 = createOrderDetail(order5,13L);
        OrderDetail orderDetail14 = createOrderDetail(order5,14L);
        OrderDetail orderDetail15 = createOrderDetail(order5,15L);

        OrderDetail orderDetail16 = createOrderDetail(order6,16L);
        OrderDetail orderDetail17 = createOrderDetail(order6,17L);
        OrderDetail orderDetail18 = createOrderDetail(order6,18L);

        OrderDetail orderDetail19 = createOrderDetail(order7,19L);
        OrderDetail orderDetail20 = createOrderDetail(order7,20L);
        OrderDetail orderDetail21 = createOrderDetail(order7,21L);

        OrderDetail orderDetail22 = createOrderDetail(order8,22L);
        OrderDetail orderDetail23 = createOrderDetail(order8,23L);
        OrderDetail orderDetail24 = createOrderDetail(order8,24L);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        orderRepository.save(order4);
        orderRepository.save(order5);
        orderRepository.save(order6);
        orderRepository.save(order7);
        orderRepository.save(order8);
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
        orderDetailRepository.save(orderDetail13);
        orderDetailRepository.save(orderDetail14);
        orderDetailRepository.save(orderDetail15);
        orderDetailRepository.save(orderDetail16);
        orderDetailRepository.save(orderDetail17);
        orderDetailRepository.save(orderDetail18);
        orderDetailRepository.save(orderDetail19);
        orderDetailRepository.save(orderDetail20);
        orderDetailRepository.save(orderDetail21);
        orderDetailRepository.save(orderDetail22);
        orderDetailRepository.save(orderDetail23);
        orderDetailRepository.save(orderDetail24);

        em.flush();
        em.clear();

        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);

        // when
        ProductPageResponse productPageResponse = orderService.getOrdersWithPaging(userId, pageRequest);

        // then
        Assertions.assertThat(productPageResponse.getTotalPage()).isEqualTo(2);
        Assertions.assertThat(productPageResponse.getContents()).hasSize(5);
        Assertions.assertThat(productPageResponse.getContents())
                .flatExtracting(v -> v.getOrderDetailResponses())
                .extracting("price")
                .containsExactlyInAnyOrder(10L,11L,12L,13L,14L,15L,16L,17L,18L,19L,20L,21L,22L,23L,24L);

    }

    @DisplayName("특정 유저의 환불 정보를 pageable해서 요구한 만큼만 가져온다")
    @Test
    void getRefundsWithPaging() {
        // given
        Long userId = 1L;
        Order order = createOrder(userId);
        Order savedOrder = orderRepository.save(order);

        OrderDetail orderDetail1 = createOrderDetail(savedOrder, 1L,1L,1L);
        OrderDetail orderDetail2 = createOrderDetail(savedOrder, 2L,2L,2L);
        OrderDetail orderDetail3 = createOrderDetail(savedOrder, 3L,3L,3L);
        OrderDetail orderDetail4 = createOrderDetail(savedOrder, 4L,4L,4L);
        OrderDetail orderDetail5 = createOrderDetail(savedOrder, 5L,5L,5L);
        OrderDetail orderDetail6 = createOrderDetail(savedOrder, 6L,6L,6L);
        OrderDetail orderDetail7 = createOrderDetail(savedOrder, 7L,7L,7L);

        changeRefundStatus(orderDetail1, RefundStatus.REJECTED);
        changeRefundStatus(orderDetail2,RefundStatus.REJECTED);
        changeRefundStatus(orderDetail3,RefundStatus.READY);
        changeRefundStatus(orderDetail4,RefundStatus.READY);
        changeRefundStatus(orderDetail5,RefundStatus.APPROVED);
        changeRefundStatus(orderDetail6,RefundStatus.APPROVED);
        changeRefundStatus(orderDetail7,RefundStatus.CREATED);

        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);
        orderDetailRepository.save(orderDetail3);
        orderDetailRepository.save(orderDetail4);
        orderDetailRepository.save(orderDetail5);
        orderDetailRepository.save(orderDetail6);
        orderDetailRepository.save(orderDetail7);

        em.flush();
        em.clear();

        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);

        // when
        RefundPageResponse refundsWithPaging = orderService.getRefundsWithPaging(userId, pageRequest);

        // then
        Assertions.assertThat(refundsWithPaging.getTotalPage()).isEqualTo(2);
        Assertions.assertThat(refundsWithPaging.getContents()).hasSize(5);
        Assertions.assertThat(refundsWithPaging.getContents())
                .extracting("refundStatus")
                .doesNotContain(RefundStatus.CREATED);

    }


    private Order createOrder(Long userId) {
        Address address = Address.builder()
                .zipcode("zipcode")
                .roadAddress("roadAddress")
                .detailAddress("detailAddress")
                .build();

        return Order.builder()
                .authId(userId)
                .address(address)
                .build();
    }

    private OrderDetail createOrderDetail(Order order, Long price) {
        return OrderDetail.builder()
                .order(order)
                .productId(1L)
                .price(price)
                .stock(100L)
                .productName("제품명")
                .productThumbnail("제품썸네일")
                .status(OrderDetailStatus.CONFIRMED)
                .build();
    }

    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .originalPrice(productPrice)
                .discountedPrice(productPrice)
                .productStock(productQuantity)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .build();
    }

    private OrderDetail createOrderDetail(Order order, Long productId, Long price, Long quantity) {
        return OrderDetail.builder()
                .order(order)
                .productId(productId)
                .price(price)
                .stock(quantity)
                .productName("제품명")
                .productThumbnail("제품썸네일")
                .status(OrderDetailStatus.CONFIRMED)
                .build();
    }

    private void changeRefundStatus(OrderDetail orderDetail, RefundStatus status){
        orderDetail.changeRefundStatus(status);
    }

}