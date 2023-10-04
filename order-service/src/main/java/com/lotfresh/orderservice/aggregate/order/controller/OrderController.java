package com.lotfresh.orderservice.aggregate.order.controller;

import com.lotfresh.orderservice.aggregate.order.service.OrderService;
import com.lotfresh.orderservice.aggregate.order.controller.request.OrderChangeStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PatchMapping("/status")
    public ResponseEntity changeStatus(@RequestBody OrderChangeStatusRequest orderChangeStatusRequest) {
        orderService.changeProductOrderStatus(orderChangeStatusRequest);
        return ResponseEntity.ok().build();
    }

}
