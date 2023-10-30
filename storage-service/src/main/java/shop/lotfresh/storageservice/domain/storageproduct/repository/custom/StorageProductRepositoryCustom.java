package shop.lotfresh.storageservice.domain.storageproduct.repository.custom;

import shop.lotfresh.storageservice.domain.storageproduct.api.request.StorageProductSearchRequest;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import java.util.List;

public interface StorageProductRepositoryCustom {

    //창고별 물품 리스트 반환(마감임박 제외)
    List<StorageProduct> findProductsByStorageId(String province);


    Integer getProductStock(String province, Long productId);

    List<StorageProduct> getProductOrderList(String province, Long productId);

    List<StorageProductSearchRequest> findSalesProductsByStorageId(Long storageId);

    Integer getSalesProductStock(String province, Long productId);

    List<StorageProduct> getSalesProductOrderList(String province, Long productId);

    List<StorageProduct> salesProductOrder(String province, Long productId, Integer stock);

}
