package shop.lotfresh.storageservice.domain.storageproduct.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.repository.StorageProductRepository;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageProductOrder;

import java.util.List;

@RestController
@RequestMapping("/storageproduct")
public class StorageProductApiController {
    private final StorageProductService storageProductService;
    private final StorageProductRepository storageProductRepository;

    public StorageProductApiController(StorageProductService storageProductService, StorageProductRepository stroageProductRepository) {
        this.storageProductService = storageProductService;
        this.storageProductRepository = stroageProductRepository;
    }

    @GetMapping("/search/{province}")
    public ResponseEntity<List<StorageProduct>> search(@PathVariable String province) {
        return ResponseEntity.ok(storageProductService.findProductsByStorageId(province));
    }


    public void nearSearch() {
        storageProductService.findNearExpiryProductsByStorageId();
    }

    @GetMapping("/stock/{province}/{productId}")
    public ResponseEntity <Integer> stock (@PathVariable String province, @PathVariable Long productId){
        return ResponseEntity.ok(storageProductService.getProductStock(province, productId));
    }

    @GetMapping("/salesstock/{province}/{productId}")
    public ResponseEntity <Integer> salesstock (@PathVariable String province, @PathVariable Long productId){
        return ResponseEntity.ok(storageProductService.getSalesProductStock(province, productId));
    }
    //order에서 주문테이블 생성 및 재고 차감을 위해 해당 물품 객체 리스트 반환
/*    @GetMapping("/test/{storageId}/{productId}")
    public ResponseEntity<List<StorageProduct>> test(@PathVariable Long storageId, @PathVariable Long productId) {
        return ResponseEntity.ok(storageProductService.getProductOrderList(storageId, productId));
    }*/

    // test 완료
    @PostMapping("/order/{province}/{productId}/{stock}")
    public ResponseEntity<List<StorageProductOrder>> order(@PathVariable String province, @PathVariable Long productId, @PathVariable Integer stock) {
        return ResponseEntity.ok(storageProductService.productOrder(province,
                productId,
                stock));
    }

    @PostMapping("/salesorder/{province}/{productId}/{stock}")
    public ResponseEntity<List<StorageProductOrder>> saelsorder(@PathVariable String province, @PathVariable Long productId, @PathVariable Integer stock) {
        return ResponseEntity.ok(storageProductService.productsalesOrder(province,
                productId,
                stock));
    }

    @PostMapping("/storageProducts")
    public ResponseEntity<StorageProduct> createStorageProduct(@RequestBody StorageProduct storageProduct) {
        StorageProduct savedStorageProduct = storageProductRepository.save(storageProduct);
        return ResponseEntity.ok(savedStorageProduct);
    }

}
