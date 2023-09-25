package com.lotfresh.orderservice.service;


import com.lotfresh.orderservice.repository.OrderRepository;
import com.lotfresh.orderservice.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;

    @Transactional
    public void insertOrder() {

    }

    @Transactional
    public void changeProductOrderStatus() {

    }

    @Transactional
    public void refundOrder() {

    }


}
