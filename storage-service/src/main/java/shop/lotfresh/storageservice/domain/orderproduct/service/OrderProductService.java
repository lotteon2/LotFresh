package shop.lotfresh.storageservice.domain.orderproduct.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import shop.lotfresh.storageservice.domain.orderproduct.api.OrderProductApiController;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderProductRequest;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.orderproduct.repository.OrderProductRepository;
import shop.lotfresh.storageservice.domain.storageproduct.service.StorageProductService;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageProductOrder;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class OrderProductService {
    private static final Logger logger = LoggerFactory.getLogger(OrderProductApiController.class);
    private final OrderProductRepository orderProductRepository;
    private final StorageProductService storageProductService;

    public OrderProductService(OrderProductRepository orderProductRepository, StorageProductService storageProductService) {
        this.orderProductRepository = orderProductRepository;
        this.storageProductService = storageProductService;
    }


    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    public void orderProduct(@RequestBody OrderProductRequest request){
        List<StorageProductOrder> productOrders = storageProductService.productOrder(request.getStorageId(), request.getProductId(), request.getStock());
        for (StorageProductOrder productOrder : productOrders) {
            OrderProduct orderProd = new OrderProduct();
            orderProd.setStorageId(productOrder.getStorageProductId());
            orderProd.setStorageProductId(productOrder.getStorageProductId());
            orderProd.setStock(productOrder.getStock());
            orderProd.setIsDeleted(0);
            orderProd.setOrderDetailId(request.getOrderDetailId());

            orderProductRepository.save(orderProd);
        }

    }
}
