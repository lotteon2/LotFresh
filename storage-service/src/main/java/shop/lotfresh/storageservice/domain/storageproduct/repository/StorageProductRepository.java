package shop.lotfresh.storageservice.domain.storageproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.repository.custom.StorageProductRepositoryCustom;


public interface StorageProductRepository extends JpaRepository<StorageProduct, Long>, StorageProductRepositoryCustom {
}
