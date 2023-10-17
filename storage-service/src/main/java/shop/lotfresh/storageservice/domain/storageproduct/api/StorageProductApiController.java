package shop.lotfresh.storageservice.domain.storageproduct.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;

import java.util.List;

@RestController
@RequestMapping("/storageproduct")
public class StorageProductApiController {
    private final StorageProductService storageProductService;

    public StorageProductApiController(StorageProductService storageProductService) {
        this.storageProductService = storageProductService;
    }

    @GetMapping("/search/{storageId}")
    public ResponseEntity<List<StorageProduct>> search(@PathVariable Long storageId) {
        return ResponseEntity.ok(storageProductService.findProductsByStorageId(storageId));
    }
    @GetMapping("/stock/{storageId}/{productId}")
    public ResponseEntity <Long> stock (@PathVariable Long storageId, @PathVariable Long productId){
        return ResponseEntity.ok(storageProductService.getProductStock(storageId, productId));
    }
/*    @GetMapping("/stock/{storageId}/{productId}")
    public ResponseEntity<List<StorageProduct>> stock(@PathVariable Long storageId, @PathVariable Long productId) {
        return ResponseEntity.ok(storageProductService.getProductOrderList(storageId, productId));
    }*/
    @PostMapping("/order/{storageId}/{productId}/{stock}")
    public ResponseEntity<List<StorageProduct>> order(@PathVariable Long storageId, @PathVariable Long productId, @PathVariable Long stock) {
        return ResponseEntity.ok(storageProductService.productOrder(storageId,
                productId,
                stock));
    }


}
