package shop.lotfresh.storageservice.domain.storageproduct.repository.custom;

import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import java.util.List;

public interface StorageProductRepositoryCustom {
    List<StorageProduct> findProductsByStorageId(Long storageId);

    Long getProductStock(Long storageId, Long productId);

    List<StorageProduct> getProductOrderList(Long storageId, Long productId);

    List<StorageProduct> productOrder(Long storageId, Long productId, Long stock);
}
