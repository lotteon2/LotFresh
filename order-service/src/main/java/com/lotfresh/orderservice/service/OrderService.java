package com.lotfresh.orderservice.service;


import com.lotfresh.orderservice.domain.Order;
import com.lotfresh.orderservice.domain.ProductOrder;
import com.lotfresh.orderservice.domain.ProductOrderId;
import com.lotfresh.orderservice.dto.OrderCreateRequest;
import com.lotfresh.orderservice.dto.ProductRequest;
import com.lotfresh.orderservice.repository.OrderRepository;
import com.lotfresh.orderservice.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                .map(productRequest -> buildProductOrder(productRequest,savedOrder))
                .collect(Collectors.toList());

        productOrderRepository.saveAll(productOrders);
    }

    @Transactional
    public void changeProductOrderStatus() {

    }

    @Transactional
    public void refundOrder() {

    }

    private ProductOrder buildProductOrder(ProductRequest productRequest, Order order) {
        return ProductOrder.builder()
                .id(buildProductOrderId(productRequest.getProductId()))
                .order(order)
                .price(productRequest.getProductPrice())
                .quantity(productRequest.getProductQuantity())
                .build();
    }
    private ProductOrderId buildProductOrderId(Long productId) {
        return ProductOrderId.builder()
                .productId(productId)
                .build();
    }


}