package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="Inventory",url="localhost:80/inventory")
public interface InventoryFeignClient {

    @PostMapping("normal/deductQuantity")
    ResponseEntity deductNormalStock(@RequestBody InventoryRequest inventoryRequest);

    @PostMapping("sales/deductQuantity")
    ResponseEntity deductSalesStock(@RequestBody InventoryRequest inventoryRequest);
}
