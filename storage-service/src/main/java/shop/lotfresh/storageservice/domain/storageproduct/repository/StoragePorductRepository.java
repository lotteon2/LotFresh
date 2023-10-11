package shop.lotfresh.storageservice.domain.storageproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.lotfresh.storageservice.domain.storage.entity.Storage;
import shop.lotfresh.storageservice.domain.storageproduct.repository.custom.StorageProductRepositoryCustom;


public interface StoragePorductRepository  extends JpaRepository<Storage, Long>, StorageProductRepositoryCustom {
}
