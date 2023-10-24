package shop.lotfresh.storageservice.domain.storageproduct.repository.custom;

import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import java.util.List;

public interface StorageProductRepositoryCustom {

    //창고별 물품 리스트 반환(마감임박 제외)
    List<StorageProduct> findProductsByStorageId(String province);

    List<StorageProduct> findNearExpiryProductsByStorageId(String province);

    Long getProductStock(String province, Long productId);

    List<StorageProduct> getProductOrderList(String province, Long productId);

    List<StorageProduct> productOrder(String province, Long productId, Long stock);

    List<StorageProduct> findSalesProductsByStorageId(String province, Long productId);

    Long getSalesProductStock(String province, Long productId);

    List<StorageProduct> getSalesProductOrderList(String province, Long productId);

    List<StorageProduct> salesProductOrder(String province, Long productId, Long stock);
}
