package com.lotfresh.orderservice.domain.orchestrator.service;

import com.lotfresh.orderservice.domain.orchestrator.Orchestrator;
import com.lotfresh.orderservice.domain.orchestrator.controller.request.OrderCreateRequest;
import com.lotfresh.orderservice.domain.orchestrator.feigns.CartFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.PaymentFeignClient;
import com.lotfresh.orderservice.domain.orchestrator.feigns.request.*;
import com.lotfresh.orderservice.domain.orchestrator.task.CartTask;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.OrderWorkflowGenerator;
import com.lotfresh.orderservice.domain.orchestrator.process.workflow.Workflow;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import com.lotfresh.orderservice.domain.order.service.response.OrderCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final OrderWorkflowGenerator orderWorkflowGenerator;
    private final OrderService orderService;
    private final CartFeignClient cartFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    public String createOrderAndRequestToPayment(OrderCreateRequest orderCreateRequest) {
        OrderCreateResponse orderCreateResponse = createOrder(orderCreateRequest);
        KakaopayReadyRequest kakaopayReadyRequest = makeKakaopayReadRequest(orderCreateResponse);
        ResponseEntity<String> result = paymentFeignClient.kakaopayReady(kakaopayReadyRequest);
        return result.getBody();
    }

    public Orchestrator orderTransaction(Long userId, Long orderId, boolean isFromCart,
                                         BiFunction<List<InventoryRequest>,PaymentRequest,Workflow> workflowGenerator) {
        List<OrderDetail> orderDetails = orderService.getOrderDetails(orderId);
        List<Long> orderDetailIds = orderDetails.stream()
                .map(OrderDetail::getId)
                .collect(Collectors.toList());

        List<InventoryRequest> inventoryRequests = makeInventoryRequests(orderDetails);
        PaymentRequest paymentRequest = makePaymentRequest();

        Workflow orderWorkflow = workflowGenerator.apply(inventoryRequests,paymentRequest);

        Orchestrator orderOrchestrator = Orchestrator.builder()
                .workflow(orderWorkflow)
                .build();
        try {
            orderOrchestrator.doTransaction();
        }catch(Exception e) {
            // TODO : 예외 및 예외처리 고도화
            orderService.revertInsertOrder(orderId, orderDetailIds);
            orderOrchestrator.revertProcess();
        }

        if(orderOrchestrator.isSuccessed() && isFromCart) {
            CartRequest cartRequest = makeCartRequest(userId,orderDetails);
            CartTask cartTask = new CartTask(cartFeignClient,cartRequest);
            cartTask.work();
        }

        return orderOrchestrator;
    }

    public Orchestrator orderNormalTransaction(Long userId, Long orderId, boolean isFromCart) {
        return orderTransaction(userId, orderId, isFromCart,
                orderWorkflowGenerator::generateNormalOrderWorkflow);
    }

    public Orchestrator orderSalesTransaction(Long userId, Long orderId, boolean isFromCart) {
        return orderTransaction(userId, orderId, isFromCart,
                orderWorkflowGenerator::generateSalesOrderWorkflow);
    }

    private OrderCreateResponse createOrder(OrderCreateRequest orderCreateRequest) {
        return orderService.insertOrder(orderCreateRequest.getProductRequests());
    }

    private KakaopayReadyRequest makeKakaopayReadRequest(OrderCreateResponse orderCreateResponse) {
        List<OrderDetailVO> orderDetails = orderCreateResponse.getOrderDetailCreateResponses().stream()
                .map(OrderDetailVO::from)
                .collect(Collectors.toList());
        return KakaopayReadyRequest.builder()
                .orderId(orderCreateResponse.getOrderId())
                .orderDetails(orderDetails)
                .build();

    }

    private List<InventoryRequest> makeInventoryRequests(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(InventoryRequest::from)
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


}
