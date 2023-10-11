package com.lotfresh.orderservice.domain.orchestrator.service;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.CartFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.CartRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.PaymentRequest;
import com.lotfresh.orderservice.domain.orchestrator.process.afterSuccess.CartTask;
import com.lotfresh.orderservice.domain.orchestrator.process.afterSuccess.Task;
import com.lotfresh.orderservice.domain.orchestrator.process.afterSuccess.TaskList;
import com.lotfresh.orderservice.domain.orchestrator.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.OrderWorkflowGenerator;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.Workflow;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final OrderWorkflowGenerator orderWorkflowGenerator;
    private final OrderService orderService;
    private final CartFeignClient cartFeignClient;

    public Orchestrator orderTransaction(OrderCreateRequest orderCreateRequest) {
        // TODO : header로부터 userId값 꺼내기
        Long userId = 1L;
        OrderCreateResponse orderCreateResponse = createOrder(orderCreateRequest);
        List<InventoryRequest> inventoryRequests = makeInventoryRequests(orderCreateResponse.getOrderDetails());
        PaymentRequest paymentRequest = makePaymentRequest();
        CartRequest cartRequest = makeCartRequest(userId,orderCreateResponse.getOrderDetails());

        TaskList taskList = makeTaskList(cartRequest);

        Workflow orderWorkflow = orderWorkflowGenerator.generateOrderWorkflow(inventoryRequests,paymentRequest);
        Orchestrator orderOrchestrator = Orchestrator.builder()
                .workflow(orderWorkflow)
                .taskList(taskList)
                .build();
        try {
            orderOrchestrator.doTransaction();
        }catch(Exception e) {
            orderService.revertInsertOrder(orderCreateResponse);
            orderOrchestrator.revertProcess();
        }

        if(orderOrchestrator.isSuccessed()) {
            orderOrchestrator.afterSuccess();
        }

        return orderOrchestrator;
    }

    private OrderCreateResponse createOrder(OrderCreateRequest orderCreateRequest) {
        return orderService.insertOrder(orderCreateRequest.getProductRequests());
    }
    private List<InventoryRequest> makeInventoryRequests(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(InventoryRequest::new)
                .collect(Collectors.toList());
    }
    private PaymentRequest makePaymentRequest() {
        return PaymentRequest.builder()
                .build();
    }

    private CartRequest makeCartRequest(Long userId, List<OrderDetail> orderDetails) {
        List<Long> productIds = orderDetails.stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        return CartRequest.builder()
                .userId(userId)
                .productIds(productIds)
                .build();
    }

    private TaskList makeTaskList(CartRequest cartRequest) {
        CartTask cartTask = new CartTask(cartFeignClient,cartRequest);
        List<Task> tasks = List.of(cartTask);
        return TaskList.builder()
                .tasks(tasks)
                .build();
    }

}
