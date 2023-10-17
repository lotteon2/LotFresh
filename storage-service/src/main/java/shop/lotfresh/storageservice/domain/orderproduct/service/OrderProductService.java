package shop.lotfresh.storageservice.domain.orderproduct.service;

import org.springframework.stereotype.Service;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.orderproduct.repository.OrderProductRepository;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }


    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Transactional
    public List<StorageProduct> orderProduct(Long storageId,Long productId, Long orderId, Long quantity) {
        return orderProductRepository.orderProduct(storageId, productId, orderId, quantity);
    }
}
