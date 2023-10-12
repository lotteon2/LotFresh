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
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
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

    @DisplayName("주문, 재고차감, 결제가 모두 정상적으로 처리됐을 때 주문이 성공한다")
    @Test
    void orderProduct() {
        // given
        BDDMockito.given(inventoryFeignClient.deductQuantity(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(cartFeignClient.removeItems(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());

        List<ProductRequest> productRequests  = List.of(
                createProductRequest(1L, 100L, 1L),
                createProductRequest(2L, 500L, 2L),
                createProductRequest(3L, 1000L, 3L),
                createProductRequest(4L, 10000L, 4L)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(productRequests)
                .build();

        Orchestrator orchestrator = orchestratorService.orderTransaction(orderCreateRequest);

        // when
        orchestrator.doTransaction();

        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        // then
        Assertions.assertThat(orderDetails)
                .extracting("productId","quantity","isDeleted")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(1L,1L,false),
                        Tuple.tuple(2L,2L,false),
                        Tuple.tuple(3L,3L,false),
                        Tuple.tuple(4L,4L,false)
                );

        for (WorkflowStep step : orchestrator.getWorkflow().getSteps()) {
            Assertions.assertThat(step.getStatus()).isEqualTo(WorkflowStepStatus.COMPLETE);
        }
        Assertions.assertThat(orchestrator.isSuccessed()).isTrue();
    }

    @DisplayName("재고 감소가 실패하면 해당 주문은 softDelete로 삭제된다")
    @Test
    void orderDeletedWhenDeductQuantityFailed(){
        // given
        BDDMockito.given(inventoryFeignClient.deductQuantity(BDDMockito.any()))
                .willThrow(new RuntimeException());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(cartFeignClient.removeItems(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.doNothing().when(kafkaProducer)
                .send(BDDMockito.any(),BDDMockito.any());

        List<ProductRequest> productRequests  = List.of(
                createProductRequest(1L, 100L, 1L),
                createProductRequest(2L, 500L, 2L),
                createProductRequest(3L, 1000L, 3L),
                createProductRequest(4L, 10000L, 4L)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(productRequests)
                .build();

        // when
        Orchestrator orchestrator = orchestratorService.orderTransaction(orderCreateRequest);

        Order order = orderRepository.findAll().get(0);
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        // then
        Assertions.assertThat(order.getIsDeleted()).isTrue();

        boolean isAllOrderDetailDeleted = orderDetails.stream()
                .allMatch(orderDetail -> orderDetail.getIsDeleted());
        Assertions.assertThat(isAllOrderDetailDeleted).isTrue();
        Assertions.assertThat(orchestrator.isSuccessed()).isFalse();

    }

    @DisplayName("결제가 실패하면 주문은 softDelete로 삭제되고, 재고는 보상 트랜잭션이 실행된다")
    @Test
    void orderAndInventoryRollbackedWhenPaymentFailed(){
        // given
        BDDMockito.given(inventoryFeignClient.deductQuantity(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willThrow(new RuntimeException());
        BDDMockito.given(cartFeignClient.removeItems(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.doNothing().when(kafkaProducer)
                .send(BDDMockito.any(),BDDMockito.any());

        List<ProductRequest> productRequests  = List.of(
                createProductRequest(1L, 100L, 1L),
                createProductRequest(2L, 500L, 2L),
                createProductRequest(3L, 1000L, 3L),
                createProductRequest(4L, 10000L, 4L)
        );
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .productRequests(productRequests)
                .build();

        // when
        Orchestrator orchestrator = orchestratorService.orderTransaction(orderCreateRequest);

        Order order = orderRepository.findAll().get(0);
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        // then
        Assertions.assertThat(order.getIsDeleted()).isTrue();

        boolean isAllOrderDetailDeleted = orderDetails.stream()
                .allMatch(orderDetail -> orderDetail.getIsDeleted());
        Assertions.assertThat(isAllOrderDetailDeleted).isTrue();

        WorkflowStep inventoryStep = orchestrator.getWorkflow().getSteps().get(0);
        Assertions.assertThat(inventoryStep.getStatus()).isEqualTo(WorkflowStepStatus.FAILED);
        Assertions.assertThat(orchestrator.isSuccessed()).isFalse();

    }

    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .productName("제품이름")
                .productThumbnail("제품썸네일")
                .build();
    }

}