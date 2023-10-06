package com.lotfresh.orderservice.domain.order.service;


import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.orchestrator.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional
    public OrderCreateResponse insertOrder(List<ProductRequest> productRequests) {
        // TODO : auth-service로부터 header로 userId 받기
        Long userId = 1L;
        Order order = Order.builder()
                .authId(userId)
                .build();
        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> orderDetails = productRequests.stream()
                .map(productRequest -> productRequest.toEntity(savedOrder))
                .collect(Collectors.toList());

        orderDetailRepository.saveAll(orderDetails);

        List<Long> orderDetailIds = orderDetails.stream()
                .map(OrderDetail::getId)
                .collect(Collectors.toList());

        return OrderCreateResponse.builder()
                .orderId(savedOrder.getId())
                .orderDetailIds(orderDetailIds)
                .build();
    }

    @Transactional
    public void revertInsertOrder(OrderCreateResponse orderCreateResponse) {
        Order order = orderRepository.findById(orderCreateResponse.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        order.softDelete();

        List<OrderDetail> orderDetails = orderDetailRepository.findAllById(orderCreateResponse.getOrderDetailIds());
        orderDetails.forEach(OrderDetail::softDelete);
    }

    @Transactional
    public void changeProductOrderStatus(OrderDetailChangeStatusRequest orderDetailChangeStatusRequest) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailChangeStatusRequest.getOrderDetailId())
                                            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        orderDetail.changeProductOrderStatus(orderDetailChangeStatusRequest.getOrderDetailStatus());
    }

    @Transactional
    public void refundOrder(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        orderDetail.changeProductOrderStatus(OrderDetailStatus.CANCELED);
    }

    @Transactional
    public void refundOrders(List<Long> orderDetailIds) {
        orderDetailRepository.findAllById(orderDetailIds)
                .forEach(orderDetail -> orderDetail.changeProductOrderStatus(OrderDetailStatus.CANCELED));
    }

}
