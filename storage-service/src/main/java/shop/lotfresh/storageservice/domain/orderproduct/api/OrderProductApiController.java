package shop.lotfresh.storageservice.domain.orderproduct.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderProductRequest;
import shop.lotfresh.storageservice.domain.orderproduct.service.OrderProductService;



@RestController
@RequestMapping("/storageorderproduct")
public class OrderProductApiController {

    private final OrderProductService orderProductService;




    public OrderProductApiController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @PostMapping("/orderproduct")
    public ResponseEntity orderProduct(@RequestBody OrderProductRequest request) {
        try {
            orderProductService.orderProduct(request);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //테스트 완료
    @PostMapping("/test/{orderId}")
    public void inventory(@PathVariable Long orderId){
        orderProductService.inventory(orderId);
    }
}

