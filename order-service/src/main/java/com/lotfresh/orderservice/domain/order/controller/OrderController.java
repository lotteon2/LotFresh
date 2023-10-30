package com.lotfresh.orderservice.domain.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lotfresh.orderservice.domain.order.controller.request.OrderDetailChangeStatusRequest;
import com.lotfresh.orderservice.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PatchMapping("/status")
    public ResponseEntity changeStatus(@Valid @RequestBody OrderDetailChangeStatusRequest orderDetailChangeStatusRequest) {
        orderService.changeProductOrderStatus(orderDetailChangeStatusRequest.getOrderDetailId(),
                orderDetailChangeStatusRequest.getOrderDetailStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mostSold")
    public ResponseEntity mostSoldProduct(@RequestParam int limitCnt) {
        return ResponseEntity.ok().body(orderService.getMostSoldProducts(limitCnt));
    }

    @GetMapping("/myOrders")
    public ResponseEntity myOrders(@RequestHeader(value = "userId", required = false) Long userId,
                                   @PageableDefault(size = 5) Pageable pageable) {
        log.warn("userId 테스트 : {}", userId);
        return ResponseEntity.ok().body(orderService.getOrdersWithPaging(userId,pageable));
    }

    @GetMapping("/refunds/me")
    public ResponseEntity refunds(@RequestHeader(value = "userId", required = false) Long userId,
                                  @PageableDefault(size = 5) Pageable pageable) {
        log.warn("userId 테스트 : {}", userId);

        return ResponseEntity.ok().body(orderService.getRefundsWithPaging(userId,pageable));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity orderDetail(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.getOrderResponse(orderId));
    }

    @GetMapping("ordersheet/{orderId}/")
    public ResponseEntity orderProducts(@RequestHeader(value = "userId" , required = false) Long userId)
            throws JsonProcessingException {
        return ResponseEntity.ok().body(orderService.getOrderSheetResponse(userId));
    }

}
