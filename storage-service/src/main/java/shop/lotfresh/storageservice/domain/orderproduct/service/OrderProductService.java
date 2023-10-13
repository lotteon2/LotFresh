package shop.lotfresh.storageservice.domain.orderproduct.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.lotfresh.storageservice.domain.orderproduct.api.request.OrderRequest;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductService orderProductRepository;


    public OrderProduct save(OrderRequest orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    public List<StorageProduct> orderProduct(Long storageId,Long productId, Long orderId, Long quantity) {
        return orderProductRepository.orderProduct(storageId, productId, orderId, quantity);
    }
}
