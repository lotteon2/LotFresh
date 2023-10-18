package shop.lotfresh.storageservice.domain.storageproduct.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageProductOrder;

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

    @GetMapping("/search/near/{storageId}")
    public ResponseEntity<List<StorageProduct>> nearSearch(@PathVariable Long storageId) {
        return ResponseEntity.ok(storageProductService.findNearExpiryProductsByStorageId(storageId));
    }

    @GetMapping("/stock/{storageId}/{productId}")
    public ResponseEntity <Long> stock (@PathVariable Long storageId, @PathVariable Long productId){
        return ResponseEntity.ok(storageProductService.getProductStock(storageId, productId));
    }
    //order에서 주문테이블 생성 및 재고 차감을 위해 해당 물품 객체 리스트 반환
/*    @GetMapping("/test/{storageId}/{productId}")
    public ResponseEntity<List<StorageProduct>> test(@PathVariable Long storageId, @PathVariable Long productId) {
        return ResponseEntity.ok(storageProductService.getProductOrderList(storageId, productId));
    }*/
    @PostMapping("/order/{storageId}/{productId}/{stock}")
    public ResponseEntity<List<StorageProductOrder>> order(@PathVariable Long storageId, @PathVariable Long productId, @PathVariable Long stock) {
        return ResponseEntity.ok(storageProductService.productOrder(storageId,
                productId,
                stock));
    }


}
