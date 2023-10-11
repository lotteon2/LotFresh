package shop.lotfresh.storageservice.domain.storageproduct.service;

import shop.lotfresh.storageservice.domain.storageproduct.api.request.StorageProductSearchRequest;
import shop.lotfresh.storageservice.domain.storageproduct.repository.StoragePorductRepository;

import javax.transaction.Transactional;

public class StorageProductService {
    private StoragePorductRepository storagePorductRepository;

    @Transactional
    public Long searchProduct(StorageProductSearchRequest request){

    }

}
