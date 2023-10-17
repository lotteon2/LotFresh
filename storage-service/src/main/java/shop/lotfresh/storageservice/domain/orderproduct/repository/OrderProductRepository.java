package shop.lotfresh.storageservice.domain.orderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.orderproduct.repository.custom.OrderProductRepositoryCustom;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {

}