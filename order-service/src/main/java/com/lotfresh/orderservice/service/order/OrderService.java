package com.lotfresh.orderservice.service.order;


import com.lotfresh.orderservice.domain.order.Order;
import com.lotfresh.orderservice.domain.productOrder.ProductOrder;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderId;
import com.lotfresh.orderservice.domain.productOrder.ProductOrderStatus;
import com.lotfresh.orderservice.dto.request.OrderChangeStatusRequest;
import com.lotfresh.orderservice.dto.request.OrderCreateRequest;
import com.lotfresh.orderservice.dto.request.OrderRefundRequest;
import com.lotfresh.orderservice.dto.request.ProductRequest;
import com.lotfresh.orderservice.dto.response.OrderCreateResponse;
import com.lotfresh.orderservice.exception.CustomException;
import com.lotfresh.orderservice.exception.ErrorCode;
import com.lotfresh.orderservice.repository.OrderRepository;
import com.lotfresh.orderservice.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;

    @Transactional
    public OrderCreateResponse insertOrder(Long userId, List<ProductRequest> productRequests) {
        Order order = Order.builder()
                .authId(userId)
                .build();
        Order savedOrder = orderRepository.save(order);

        List<ProductOrder> productOrders = productRequests.stream()
                .map(productRequest -> productRequest.toEntity(savedOrder))
                .collect(Collectors.toList());

        productOrderRepository.saveAll(productOrders);

        List<ProductOrderId> productIds = productOrders.stream()
                .map(ProductOrder::getId)
                .collect(Collectors.toList());

        return OrderCreateResponse.builder()
                .orderId(savedOrder.getId())
                .productIds(productIds)
                .build();
    }

    @Transactional
    public void revertInsertOrder(OrderCreateResponse orderCreateResponse) {
        Order order = orderRepository.findById(orderCreateResponse.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        order.softDelete();

        List<ProductOrder> productOrders = productOrderRepository.findAllById(orderCreateResponse.getProductIds());
        productOrders.stream().forEach(ProductOrder::softDelete);
    }

    @Transactional
    public void changeProductOrderStatus(OrderChangeStatusRequest orderChangeStatusRequest) {
        ProductOrder productOrder = productOrderRepository.findById(orderChangeStatusRequest.getProductOrderId())
                                            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        productOrder.changeProductOrderStatus(orderChangeStatusRequest.getProductOrderStatus());
    }

    @Transactional
    public void refundOrder(ProductOrderId productOrderId) {
        ProductOrder productOrder = productOrderRepository.findById(productOrderId)
                .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        productOrder.changeProductOrderStatus(ProductOrderStatus.CANCELED);
    }

}
