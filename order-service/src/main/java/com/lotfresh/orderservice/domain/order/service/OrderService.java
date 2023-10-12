package com.lotfresh.orderservice.domain.order.service;


import com.lotfresh.orderservice.domain.orchestrator.controller.request.ProductRequest;
import com.lotfresh.orderservice.domain.orchestrator.service.response.OrderCreateResponse;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import com.lotfresh.orderservice.domain.order.entity.Order;
import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.entity.status.OrderDetailStatus;
import com.lotfresh.orderservice.domain.order.repository.OrderDetailRepository;
import com.lotfresh.orderservice.domain.order.repository.OrderRepository;
import com.lotfresh.orderservice.domain.order.service.response.BestProductsResponse;
import com.lotfresh.orderservice.domain.order.service.response.OrderDetailResponse;
import com.lotfresh.orderservice.domain.order.service.response.OrderResponse;
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

        return OrderCreateResponse.builder()
                .order(order)
                .orderDetails(orderDetails)
                .build();
    }

    @Transactional
    public void revertInsertOrder(OrderCreateResponse orderCreateResponse) {
        Order order = orderRepository.findById(orderCreateResponse.getOrder().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        order.softDelete();

        List<Long> orderDetailIds = orderCreateResponse.getOrderDetails().stream()
                .map(OrderDetail::getId)
                .collect(Collectors.toList());

        List<OrderDetail> orderDetails = orderDetailRepository.findAllById(orderDetailIds);
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

    public OrderResponse getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailResponse::entityToDto)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderCreatedTime(order.getCreatedAt())
                .orderDetailResponses(orderDetailResponses)
                .build();
    }

    // TODO : 매일 정해진 시간에 Redisd에 값 전달(배치처리 or 카프카 커넥트 등등)
    public List<BestProductsResponse> getMostSoldProducts(int limitCnt) {
        return orderDetailRepository.mostSoldProducts(limitCnt);
    }

}
