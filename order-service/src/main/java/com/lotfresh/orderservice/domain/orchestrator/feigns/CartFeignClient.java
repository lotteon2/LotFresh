package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.CartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="cart-service")
public interface CartFeignClient {
    @PostMapping("/carts/removeItems")
    ResponseEntity removeItems(@RequestBody CartRequest cartRequest,
                               @RequestHeader(value = "userId") Long userId);

}
