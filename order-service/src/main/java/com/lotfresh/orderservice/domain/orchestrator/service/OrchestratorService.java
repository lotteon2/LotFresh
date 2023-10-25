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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrchestratorService {
    private final OrderWorkflowGenerator orderWorkflowGenerator;
    private final OrderService orderService;
    private final CartFeignClient cartFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    public String createOrderAndRequestToPayment(OrderCreateRequest orderCreateRequest) {
        OrderCreateResponse orderCreateResponse = createOrder(orderCreateRequest);
        KakaopayReadyRequest kakaopayReadyRequest =
                makeKakaopayReadyRequest(orderCreateResponse.getOrderId(),orderCreateRequest);
        ResponseEntity<String> result = paymentFeignClient.kakaopayReady(kakaopayReadyRequest);
        return result.getBody();
    }

    public Orchestrator orderTransaction(Long userId, String userProvince, Long orderId, boolean isFromCart,
                                         BiFunction<InventoryRequest,PaymentRequest,Workflow> workflowGenerator) {
        List<OrderDetail> orderDetails = orderService.getOrderDetails(orderId);
        List<Long> orderDetailIds = orderDetails.stream()
                .map(OrderDetail::getId)
                .collect(Collectors.toList());

        InventoryRequest inventoryRequest = makeInventoryRequest(orderDetails,userProvince,orderId);
        PaymentRequest paymentRequest = makePaymentRequest();

        Workflow orderWorkflow = workflowGenerator.apply(inventoryRequest,paymentRequest);

        Orchestrator orderOrchestrator = Orchestrator.builder()
                .workflow(orderWorkflow)
                .build();
        try {
            orderOrchestrator.doTransaction();
            log.info("orderTranscation 성공");
        }catch(Exception e) {
            log.info("orderTransaction 실패");
            orderService.revertInsertOrder(orderId, orderDetailIds);
            orderOrchestrator.revertProcess();
            throw e;
        }

        if(isFromCart) {
            CartRequest cartRequest = makeCartRequest(userId,orderDetails);
            CartTask cartTask = new CartTask(cartFeignClient,cartRequest);
            cartTask.work();
        }

        return orderOrchestrator;
    }

    public Orchestrator orderNormalTransaction(Long userId, String userProvince, Long orderId, boolean isFromCart) {
        return orderTransaction(userId, userProvince, orderId, isFromCart,
                orderWorkflowGenerator::generateNormalOrderWorkflow);
    }

    public Orchestrator orderSalesTransaction(Long userId, String userProvince, Long orderId, boolean isFromCart) {
        return orderTransaction(userId, userProvince, orderId, isFromCart,
                orderWorkflowGenerator::generateSalesOrderWorkflow);
    }

    private OrderCreateResponse createOrder(OrderCreateRequest orderCreateRequest) {
        return orderService.insertOrder(orderCreateRequest.getProductRequests());
    }

    private KakaopayReadyRequest makeKakaopayReadyRequest(Long orderId, OrderCreateRequest orderCreateRequest) {
        List<OrderDetailVO> orderDetails = orderCreateRequest.getProductRequests().stream()
                .map(OrderDetailVO::from)
                .collect(Collectors.toList());

        return KakaopayReadyRequest.builder()
                .orderId(orderId)
                .orderDetails(orderDetails)
                .build();

    }

    private InventoryRequest makeInventoryRequest(List<OrderDetail> orderDetails, String userProvince, Long orderId) {
        List<ProductInfo> productInfos = orderDetails.stream()
                .map(ProductInfo::from)
                .collect(Collectors.toList());

        return InventoryRequest.builder()
                .productInfos(productInfos)
                .province(userProvince)
                .orderId(orderId)
                .build();
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
