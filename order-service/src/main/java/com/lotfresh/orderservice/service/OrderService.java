package com.lotfresh.orderservice.service;


import com.lotfresh.orderservice.domain.order.Order;
import com.lotfresh.orderservice.domain.productOrder.ProductOrder;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderStatus;
import com.lotfresh.orderservice.dto.request.OrderChangeStatusRequest;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.OrderRefundRequest;
import com.lotfresh.orderservice.dto.request.ProductRequest;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.exception.ErrorCode;
import com.lotfresh.orderservice.repository.OrderRepository;
import com.lotfresh.orderservice.repository.ProductOrderRepository;
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
    private final ProductOrderRepository productOrderRepository;

    @Transactional
    public void insertOrder(OrderCreateRequest orderCreateRequest) {
        Order order = Order.builder()
                .authId(orderCreateRequest.getUserId())
                .build();
        Order savedOrder = orderRepository.save(order);

        List<ProductRequest> productRequests = orderCreateRequest.getProductRequests();
        List<ProductOrder> productOrders = productRequests.stream()
                .map(productRequest -> productRequest.toEntity(savedOrder))
                .collect(Collectors.toList());

        productOrderRepository.saveAll(productOrders);
    }

    @Transactional
    public void changeProductOrderStatus(OrderChangeStatusRequest orderChangeStatusRequest) {
        ProductOrder productOrder = productOrderRepository.findById(orderChangeStatusRequest.getProductOrderId())
                                            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        productOrder.changeProductOrderStatus(orderChangeStatusRequest.getProductOrderStatus());
    }

    @Transactional
    public void refundOrder(OrderRefundRequest orderRefundRequest) {
        ProductOrder productOrder = productOrderRepository.findById(orderRefundRequest.getProductOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        productOrder.changeProductOrderStatus(ProductOrderStatus.CANCELED);
    }

}
