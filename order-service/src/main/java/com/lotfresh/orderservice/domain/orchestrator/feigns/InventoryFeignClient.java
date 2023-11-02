package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="storage-service")
public interface InventoryFeignClient {

    @PostMapping("/storageorderproduct/orderproduct")
    ResponseEntity deductNormalStock(@RequestBody InventoryRequest inventoryRequest,
                                     @RequestHeader(value = "userId") Long userId);

    @PostMapping("/storageorderproduct/ordersalesproduct")
    ResponseEntity deductSalesStock(@RequestBody InventoryRequest inventoryRequest,
                                    @RequestHeader(value = "userId") Long userId);
}
