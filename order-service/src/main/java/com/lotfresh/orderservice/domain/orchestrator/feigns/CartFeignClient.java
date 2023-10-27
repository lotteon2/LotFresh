package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.CartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="Cart",url="cart-service:8082/cart")
public interface CartFeignClient {
    @PostMapping("/removeItems")
    ResponseEntity removeItems(@RequestBody CartRequest cartRequest);

}
