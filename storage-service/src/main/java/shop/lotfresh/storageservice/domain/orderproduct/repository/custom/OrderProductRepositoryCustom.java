package shop.lotfresh.storageservice.domain.orderproduct.repository.custom;

import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

import java.util.List;

public interface OrderProductRepositoryCustom {

    List<OrderProduct> inventory(Long orderId);
    List<StorageProduct> orderProduct(Long storageId, Long productId, Long orderId, Long quantity);
}
