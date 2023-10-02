package com.lotfresh.orderservice.service.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.OrderWorkflow;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowStepStatus;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.ProductRequest;
import com.lotfresh.orderservice.service.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.service.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.service.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private OrderService orderService;
    @MockBean
    private InventoryFeignClient inventoryFeignClient;

    @MockBean
    private PaymentFeignClient paymentFeignClient;

    private OrchestratorService orchestratorService;

    @BeforeEach
    public void beforeEach() {
        orchestratorService =
                new OrchestratorService(orderService,inventoryFeignClient,paymentFeignClient);

    }


    @DisplayName("주문, 재고차감, 결제가 모두 정상적으로 처리됐을 때 주문이 성공한다")
    @Test
    void orderProduct() {
        // given
        BDDMockito.given(inventoryFeignClient.deductQuantity(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());

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

        OrderWorkflow orderWorkflow = orchestratorService.generateOrderWorkflow(orderCreateRequest);

        // when
        orchestratorService.doTransaction(orderWorkflow);

        // then
        for (WorkflowStep step : orderWorkflow.getSteps()) {
            Assertions.assertThat(step.getStatus()).isEqualTo(WorkflowStepStatus.COMPLETE);
        }
    }

    @DisplayName("결제가 실패하면 주문과 재고차감의 보상 트랜잭션이 실행된다")
    @Test
    void paymentFail(){
        // given
        BDDMockito.given(inventoryFeignClient.deductQuantity(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
                .willThrow(new RuntimeException());


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

        OrderWorkflow orderWorkflow = orchestratorService.generateOrderWorkflow(orderCreateRequest);

        // when
        orchestratorService.doTransaction(orderWorkflow);

        // then
        Assertions.assertThat(orderWorkflow.getSteps().get(0).getStatus()).isEqualTo(WorkflowStepStatus.FAILED);
        Assertions.assertThat(orderWorkflow.getSteps().get(1).getStatus()).isEqualTo(WorkflowStepStatus.FAILED);

    }



    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

}