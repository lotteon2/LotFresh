package com.lotfresh.orderservice.domain.order.service;


import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.order.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import com.lotfresh.orderservice.domain.order.service.response.*;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        List<OrderDetailCreateResponse> orderDetailCreateResponses = orderDetails.stream()
                .map(OrderDetailCreateResponse::from)
                .collect(Collectors.toList());

        return OrderCreateResponse.builder()
                .orderId(order.getId())
                .orderDetailCreateResponses(orderDetailCreateResponses)
                .build();
    }

    @Transactional
    public void revertInsertOrder(Long orderId, List<Long> orderDetailIds) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        order.softDelete();

        List<OrderDetail> orderDetails = orderDetailRepository.findAllById(orderDetailIds);
        orderDetails.forEach(OrderDetail::softDelete);
    }

    @Transactional
    public void changeProductOrderStatus(Long orderDetailId, OrderDetailStatus newStatus) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                                            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        orderDetail.changeProductOrderStatus(newStatus);
    }

    @Transactional
    public void refundOrder(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        orderDetail.changeProductOrderStatus(OrderDetailStatus.CANCELED);
    }

    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }

    public OrderResponse getOrderResponse(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderCreatedTime(order.getCreatedAt())
                .orderDetailResponses(orderDetailResponses)
                .build();
    }

    public ProductPageResponse getOrdersWithPaging(Long userId, Pageable pageable) {
        // TODO : header에서 userId 받기
        userId = 1L;
        Page<Order> orderPages = orderRepository.getOrdersWithPaging(userId, pageable);

        List<OrderResponse> contents = orderPages.getContent().stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());

        return ProductPageResponse.builder()
                .contents(contents)
                .totalPage(orderPages.getTotalPages())
                .build();
    }

    public List<BestProductsResponse> getMostSoldProducts(int limitCnt) {
        return orderDetailRepository.mostSoldProducts(limitCnt);
    }

}
