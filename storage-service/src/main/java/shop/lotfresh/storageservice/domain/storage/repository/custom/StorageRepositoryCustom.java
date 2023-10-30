package shop.lotfresh.storageservice.domain.storage.repository.custom;

import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

import java.util.List;

public interface StorageRepositoryCustom {
    List<StorageProduct> orderProduct(Long storageId, Long productId, Long orderId, Long quantity);
//    Optional<Storage> findByIdFetch(Long id);

}
