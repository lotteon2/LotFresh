package shop.lotfresh.storageservice.domain.orderproduct.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderRequest;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.orderproduct.service.OrderProductService;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;


@RestController
@RequestMapping("/api/orders")
public class OrderProductApiController {

    private final OrderProductService orderProductService;
    private final StorageProductService storageProductService;

    public OrderProductApiController(OrderProductService orderProductService, StorageProductService storageProductService) {
        this.orderProductService = orderProductService;
        this.storageProductService = storageProductService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {

        OrderRequest order = new OrderRequest();
        order.setStorage_product_id(request.getStorage_product_id());
        order.setOrder_detail_id(request.getOrder_detail_id());
        order.setQuantity(request.getQuantity());


        OrderProduct savedOrder = orderProductService.save(order);

        try {
            storageProductService.productOrder(savedOrder.getId(),savedOrder.getStorage_product_id(), savedOrder.getQuantity());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the product stock.");
        }

        return ResponseEntity.ok(savedOrder);
    }
}

