package shop.lotfresh.storageservice.domain.storageproduct.api.request;

import lombok.Getter;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

@Getter
public class StorageProductSearchRequest {

    private final StorageProduct storageProduct;
    private final String province;

    public StorageProductSearchRequest(StorageProduct storageProduct, String province) {
        this.storageProduct = storageProduct;
        this.province = province;
    }

}
