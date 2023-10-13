package shop.lotfresh.storageservice.domain.orderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.storage.repository.custom.StorageRepositoryCustom;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, StorageRepositoryCustom {

}