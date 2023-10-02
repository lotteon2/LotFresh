package com.lotfresh.orderservice.service.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.*;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.OrderRefundRequest;
import com.lotfresh.orderservice.service.order.OrderService;
import com.lotfresh.orderservice.service.orchestrator.steps.InventoryStep;
import com.lotfresh.orderservice.service.orchestrator.steps.OrderStep;
import com.lotfresh.orderservice.service.orchestrator.steps.PaymentStep;
import com.lotfresh.orderservice.service.orchestrator.feigns.InventoryFeignClient;
import com.lotfresh.orderservice.service.orchestrator.feigns.PaymentFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final OrderService orderService;
    private final InventoryFeignClient inventoryFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    public void orderProduct(OrderCreateRequest orderCreateRequest) {
        OrderWorkflow orderWorkflow = generateOrderWorkflow(orderCreateRequest);
        doTransaction(orderWorkflow);
    }

    public void refundProduct(OrderRefundRequest orderRefundRequest) {

    }

    public void doTransaction(Workflow workflow) {
        try{
            for (WorkflowStep step : workflow.getSteps()) {
                step.process();
            }
        }catch (Exception e){
            revertOrder(workflow);
        }
    }

    public OrderWorkflow generateOrderWorkflow(OrderCreateRequest orderCreateRequest){
        List<WorkflowStep> workflowSteps = List.of(new OrderStep(orderService, orderCreateRequest),
                new InventoryStep(inventoryFeignClient, orderCreateRequest.getProductRequests()),
                new PaymentStep(paymentFeignClient, orderCreateRequest.getUserId()));

        return OrderWorkflow.builder().workflowSteps(workflowSteps).build();
    }

    private void revertOrder(Workflow workflow) {
        workflow.getSteps().stream()
                .filter(WorkflowStep::isRevertTarget)
                .forEach(WorkflowStep::revert);
    }

}
