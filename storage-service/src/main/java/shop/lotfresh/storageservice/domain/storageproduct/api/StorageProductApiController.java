package shop.lotfresh.storageservice.domain.storageproduct.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;

@RestController
@RequestMapping("/storageproduct")
public class StorageProductApiController {
    private final StorageProductService storageProductService;


    public StorageProductApiController(StorageProductService storageProductService) {
        this.storageProductService = storageProductService;
    }
}
