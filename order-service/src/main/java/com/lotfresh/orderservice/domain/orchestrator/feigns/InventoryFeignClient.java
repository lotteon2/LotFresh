package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="Inventory",url="localhost:80/inventory")
public interface InventoryFeignClient {

    @PostMapping("/deductQuantity")
    ResponseEntity deductQuantity(@RequestBody List<InventoryRequest> inventoryRequests);
    @PostMapping("/revertDeductQuantity")
    ResponseEntity revertDeductQuantity(@RequestBody List<InventoryRequest> inventoryRequests);
}
