package com.lotfresh.orderservice.domain.orchestrator.service;

import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.feigns.CartFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.kafka.KafkaProducer;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.step.WorkflowStepStatus;
import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import com.lotfresh.orderservice.exception.SagaException;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class OrchestratorServiceTest {
    @MockBean
    private InventoryFeignClient inventoryFeignClient;

    @MockBean
    private PaymentFeignClient paymentFeignClient;

    @MockBean
    private CartFeignClient cartFeignClient;

    @MockBean
    private KafkaProducer kafkaProducer;

    @Autowired
    private OrchestratorService orchestratorService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private EntityManager em;

    @DisplayName("사용자의 주문요청을 받아 주문을 생성한 뒤 QR코드를 반환한다")
    @Test
    void createOrderAndReturnQRCode() {
        // given
        BDDMockito.given(paymentFeignClient.kakaopayReady(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().body("URL"));

        List<ProductRequest> productRequests  = List.of(
                createProductRequest(1L, 100L, 1L),
                createProductRequest(2L, 500L, 2L),
                createProductRequest(3L, 1000L, 3L),
                createProductRequest(4L, 10000L, 4L)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(productRequests)
                .isFromCart(true)
                .build();

        // when
        String url = orchestratorService.createOrderAndRequestToPayment(orderCreateRequest);

        em.flush();
        em.clear();

        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        // then
        Assertions.assertThat(url).isEqualTo("URL");
        Assertions.assertThat(orderDetails)
                .extracting("productId","stock","isDeleted")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(1L,1L,false),
                        Tuple.tuple(2L,2L,false),
                        Tuple.tuple(3L,3L,false),
                        Tuple.tuple(4L,4L,false)
                );
    }

    @DisplayName("주문, 재고차감, 결제가 모두 정상적으로 처리됐을 때 주문이 성공한다")
    @Test
    void orchestratorProcessSucceed() {
        // given
        BDDMockito.given(inventoryFeignClient.deductNormalStock(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(cartFeignClient.removeItems(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());

        Order order = createOrder(1L);

        OrderDetail orderDetail1 = createOrderDetail(order,1L);
        OrderDetail orderDetail2 = createOrderDetail(order,2L);
        OrderDetail orderDetail3 = createOrderDetail(order,3L);

        orderRepository.save(order);
        orderDetailRepository.saveAll(List.of(orderDetail1,orderDetail2,orderDetail3));

        // when
        Orchestrator orchestrator = orchestratorService.orderNormalTransaction(1L,"SEOUL", order.getId(),false);

        // then
        for (WorkflowStep step : orchestrator.getWorkflow().getSteps()) {
            Assertions.assertThat(step.getStatus()).isEqualTo(WorkflowStepStatus.COMPLETE);
        }
    }

    @DisplayName("장바구니로부터의 구매는 구매 성공 후 장바구니에게 삭제 요청을 보낸다")
    @Test
    void requestDeleteToCartWhenProcessSucceeded() {
        // given
        BDDMockito.given(inventoryFeignClient.deductNormalStock(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(cartFeignClient.removeItems(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());

        Order order = createOrder(1L);

        orderRepository.save(order);

        // when
        orchestratorService.orderNormalTransaction(1L,"SEOUL", order.getId(),true);

        // then
        BDDMockito.verify(cartFeignClient).removeItems(BDDMockito.any());

    }

    @DisplayName("재고 감소가 실패하면 해당 주문은 softDelete로 삭제된다")
    @Test
    void orderDeletedWhenDeductQuantityFailed(){
        // given
        BDDMockito.given(inventoryFeignClient.deductNormalStock(BDDMockito.any()))
                .willThrow(new RuntimeException());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(cartFeignClient.removeItems(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.doNothing().when(kafkaProducer)
                .send(BDDMockito.any(),BDDMockito.any());

        Order order = createOrder(1L);

        OrderDetail orderDetail1 = createOrderDetail(order,1L);
        OrderDetail orderDetail2 = createOrderDetail(order,2L);
        OrderDetail orderDetail3 = createOrderDetail(order,3L);

        orderRepository.save(order);
        orderDetailRepository.saveAll(List.of(orderDetail1,orderDetail2,orderDetail3));

        // when
        Assertions.assertThatThrownBy(() -> orchestratorService.orderNormalTransaction(1L,"SEOUL",order.getId(),true))
                .isInstanceOf(SagaException.class);


        em.flush();
        em.clear();

        Order canceledOrder = orderRepository.findById(order.getId()).get();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        // then
        Assertions.assertThat(canceledOrder.getIsDeleted()).isTrue();

        boolean isAllOrderDetailDeleted = orderDetails.stream()
                .allMatch(OrderDetail::getIsDeleted);
        Assertions.assertThat(isAllOrderDetailDeleted).isTrue();

    }

    @DisplayName("결제가 실패하면 주문은 softDelete로 삭제되고, 재고는 보상 트랜잭션이 실행된다")
    @Test
    void orderAndInventoryRollbackedWhenPaymentFailed(){
        // given
        BDDMockito.given(inventoryFeignClient.deductNormalStock(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willThrow(new RuntimeException());
        BDDMockito.given(cartFeignClient.removeItems(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.doNothing().when(kafkaProducer)
                .send(BDDMockito.any(),BDDMockito.any());

        Order order = createOrder(1L);

        OrderDetail orderDetail1 = createOrderDetail(order,1L);
        OrderDetail orderDetail2 = createOrderDetail(order,2L);
        OrderDetail orderDetail3 = createOrderDetail(order,3L);

        orderRepository.save(order);
        orderDetailRepository.saveAll(List.of(orderDetail1,orderDetail2,orderDetail3));

        // when
        Assertions.assertThatThrownBy(() -> orchestratorService.orderNormalTransaction(1L,"SEOUL",order.getId(),true))
                .isInstanceOf(SagaException.class);

        em.flush();
        em.clear();

        Order canceledOrder = orderRepository.findById(order.getId()).get();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        // then
        Assertions.assertThat(canceledOrder.getIsDeleted()).isTrue();

        boolean isAllOrderDetailDeleted = orderDetails.stream()
                .allMatch(OrderDetail::getIsDeleted);
        Assertions.assertThat(isAllOrderDetailDeleted).isTrue();

    }

    private ProductRequest createProductRequest(Long productId, Long price, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .originalPrice(price)
                .discountedPrice(price)
                .productStock(productQuantity)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .build();
    }

    private Order createOrder(Long userId) {
        return Order.builder()
                .authId(userId)
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

}