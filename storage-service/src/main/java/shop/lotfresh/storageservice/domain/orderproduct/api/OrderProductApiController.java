package shop.lotfresh.storageservice.domain.orderproduct.api;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.lotfresh.storageservice.domain.orderproduct.service.OrderProductService;


@RestController
@RequestMapping("/order")
public class OrderProductApiController {
    private final OrderProductService orderProductService;


    public OrderProductApiController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }




}
