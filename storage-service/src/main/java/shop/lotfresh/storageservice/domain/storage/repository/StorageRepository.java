package shop.lotfresh.storageservice.domain.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.lotfresh.storageservice.domain.storage.entity.Storage;
import shop.lotfresh.storageservice.domain.storage.repository.custom.StorageRepositoryCustom;
public interface StorageRepository extends JpaRepository<Storage, Long>, StorageRepositoryCustom {

}