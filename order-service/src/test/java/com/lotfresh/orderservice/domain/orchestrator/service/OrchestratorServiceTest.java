package com.lotfresh.orderservice.domain.orchestrator.service;

import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.step.WorkflowStepStatus;
import com.lotfresh.orderservice.domain.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import org.assertj.core.api.Assertions;
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

    @Autowired
    private OrchestratorService orchestratorService;

    @DisplayName("주문, 재고차감, 결제가 모두 정상적으로 처리됐을 때 주문이 성공한다")
    @Test
    void orderProduct() {
        // given
        BDDMockito.given(inventoryFeignClient.deductQuantity(BDDMockito.any()))
                .willReturn(ResponseEntity.ok().build());
        BDDMockito.given(paymentFeignClient.requestPayment(BDDMockito.any()))
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

        // then
        for (WorkflowStep step : orchestrator.getWorkflow().getSteps()) {
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

        // then
        Assertions.assertThat(orchestrator.getWorkflow().getSteps().get(0).getStatus()).isEqualTo(WorkflowStepStatus.FAILED);
    }


    private ProductRequest createProductRequest(Long productId, Long productPrice, Long productQuantity){
        return ProductRequest.builder()
                .productId(productId)
                .productPrice(productPrice)
                .productQuantity(productQuantity)
                .build();
    }

}