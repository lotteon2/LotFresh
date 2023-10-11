package shop.lotfresh.storageservice.domain.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.storageservice.domain.storage.api.request.StorageCreateRequest;
import shop.lotfresh.storageservice.domain.storage.entity.Storage;
import shop.lotfresh.storageservice.domain.storage.repository.StorageRepository;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;

    @Transactional
    public Long createStorage(StorageCreateRequest request) {

        Storage savedProduct = storageRepository.save(request.toEntity());
        return savedProduct.getId();
    }
}
