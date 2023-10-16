package shop.lotfresh.storageservice.domain.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.lotfresh.storageservice.domain.storage.api.request.StorageCreateRequest;
import shop.lotfresh.storageservice.domain.storage.entity.Storage;
import shop.lotfresh.storageservice.domain.storage.repository.StorageRepository;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;

    @Transactional
    public Long createStorage(StorageCreateRequest request) {
        Storage savedProduct = storageRepository.save(request.toEntity());
        return savedProduct.getId();
    }
}
