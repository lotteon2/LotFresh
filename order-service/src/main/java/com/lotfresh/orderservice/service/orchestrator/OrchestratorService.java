package com.lotfresh.orderservice.service.orchestrator;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowStep;
import com.lotfresh.orderservice.domain.orchestrator.WorkflowStepStatus;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
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
        Orchestrator orchestrator = new Orchestrator(generateSteps(orderCreateRequest));
        for (WorkflowStep step : orchestrator.getSteps()) {
            try{
                step.process();
            }catch (Exception e){
                revertOrder(orchestrator);
            }
        }
    }

    private List<WorkflowStep> generateSteps(OrderCreateRequest orderCreateRequest){
        return List.of(new OrderStep(orderService,orderCreateRequest),
                new InventoryStep(inventoryFeignClient,orderCreateRequest.getProductRequests()),
                new PaymentStep(paymentFeignClient, orderCreateRequest.getUserId()));
    }

    private void revertOrder(Orchestrator orchestrator) {
        for (WorkflowStep step : orchestrator.getSteps()) {
            if(step.getStatus() == WorkflowStepStatus.COMPLETE) {
                step.revert();
            }
        }
    }

}
