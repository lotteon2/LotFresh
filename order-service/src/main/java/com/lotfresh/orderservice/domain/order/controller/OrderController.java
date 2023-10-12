package com.lotfresh.orderservice.domain.order.controller;

import com.lotfresh.orderservice.domain.order.service.OrderService;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PatchMapping("/status")
    public ResponseEntity changeStatus(@Valid @RequestBody OrderDetailChangeStatusRequest orderDetailChangeStatusRequest) {
        orderService.changeProductOrderStatus(orderDetailChangeStatusRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mostSold")
    public ResponseEntity mostSoldProduct(@RequestParam int limitCnt) {
        return ResponseEntity.ok().body(orderService.getMostSoldProducts(limitCnt));
    }

}
