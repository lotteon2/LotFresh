package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="Inventory",url="storage-service:8086/storageorderproduct")
public interface InventoryFeignClient {

    @PostMapping("/orderproduct")
    ResponseEntity deductNormalStock(@RequestBody InventoryRequest inventoryRequest);

    @PostMapping("sales/deductQuantity")
    ResponseEntity deductSalesStock(@RequestBody InventoryRequest inventoryRequest);
}
