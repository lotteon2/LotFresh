package shop.lotfresh.storageservice.domain.orderproduct.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderProductRequest;
import shop.lotfresh.storageservice.domain.orderproduct.service.OrderProductService;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;



@RestController
@RequestMapping("/storageorderproduct")
public class OrderProductApiController {

    private final OrderProductService orderProductService;
    private final StorageProductService storageProductService;



    public OrderProductApiController(OrderProductService orderProductService, StorageProductService storageProductService) {
        this.orderProductService = orderProductService;
        this.storageProductService = storageProductService;
    }

    @PostMapping("/orderproduct")
    public ResponseEntity orderProduct(@RequestBody OrderProductRequest request) {
        orderProductService.orderProduct(request);
        return null;
    }
}

