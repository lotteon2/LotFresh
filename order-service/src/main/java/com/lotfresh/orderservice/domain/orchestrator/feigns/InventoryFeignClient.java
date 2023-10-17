package com.lotfresh.orderservice.domain.orchestrator.feigns;

import com.lotfresh.orderservice.domain.orchestrator.feigns.request.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="Inventory",url="localhost:80/inventory")
public interface InventoryFeignClient {

    @PostMapping("normal/deductQuantity")
    ResponseEntity deductNormalQuantity(@RequestBody List<InventoryRequest> inventoryRequests);

    @PostMapping("sales/deductQuantity")
    ResponseEntity deductSalesQuantity(@RequestBody List<InventoryRequest> inventoryRequests);
}
