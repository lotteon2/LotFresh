package com.lotfresh.orderservice.controller;

import com.lotfresh.orderservice.dto.OrderCreateRequest;
import com.lotfresh.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity insertOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        orderService.insertOrder(orderCreateRequest);
        return ResponseEntity.ok().build();
    }

}
