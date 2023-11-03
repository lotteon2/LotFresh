package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.CartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="cart-service", url = "https://www.lot-fresh.shop/cart-service")
public interface CartFeignClient {
    @DeleteMapping("/carts/products")
    ResponseEntity removeItems(@RequestBody CartRequest cartRequest,
                               @RequestHeader(value = "userId") Long userId);

}
