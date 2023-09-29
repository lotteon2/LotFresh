package com.lotfresh.orderservice.service.orchestrator.feigns;

import com.lotfresh.orderservice.dto.request.ProductRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="Inventory",url="localhost:80/storage")
public interface InventoryFeignClient {

    @PostMapping("/deductQuantity")
    ResponseEntity deductQuantity(@RequestBody List<ProductRequest> productRequests);
    @PostMapping("/revertDeductQuantity")
    ResponseEntity revertDeductQuantity(@RequestBody List<ProductRequest> productRequests);
}
