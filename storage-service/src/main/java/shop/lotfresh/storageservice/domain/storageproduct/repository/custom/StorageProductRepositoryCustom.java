package shop.lotfresh.storageservice.domain.storageproduct.repository.custom;

import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import java.util.List;

public interface StorageProductRepositoryCustom {
    List<StorageProduct> findProductsByStorageId(Long storageId);

    List<StorageProduct> findNearExpiryProductsByStorageId(Long storageId);

    Long getProductStock(Long storageId, Long productId);

    List<StorageProduct> getProductOrderList(Long storageId, Long productId);

    List<StorageProduct> productOrder(Long storageId, Long productId, Long stock);

    List<StorageProduct> findSalesProductsByStorageId(Long storageId, Long productId);

    Long getSalesProductStock(Long storageId, Long productId);

    List<StorageProduct> getSalesProductOrderList(Long storageId, Long productId);

    List<StorageProduct> salesProductOrder(Long storageId, Long productId, Long stock);
}
