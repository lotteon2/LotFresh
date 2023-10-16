package shop.lotfresh.storageservice.domain.storageproduct.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

import javax.persistence.PersistenceContext;
import java.util.List;

import static shop.lotfresh.storageservice.domain.storageproduct.entity.QStorageProduct.storageProduct;


@RequiredArgsConstructor
public class StorageProductRepositoryCustomImpl implements StorageProductRepositoryCustom{

    @PersistenceContext
    private final JPAQueryFactory queryFactory;


    @Override
    public List<StorageProduct> findProductsByStorageId(Long storageId) {
        return queryFactory
                .selectFrom(storageProduct)
                .where(storageProduct.storageId.eq(storageId))
                .fetch();
    }

    @Override
    public List<StorageProduct> productStockCheck(Long storageId, Long productId) {
        return queryFactory.selectFrom(storageProduct)
                .where(storageProduct.storageId.eq(storageId)
                        .and(storageProduct.productId.eq(productId)))
                .fetch();
    }

    @Override
    public List<StorageProduct> productOrder(Long storageId, Long productId, Long stock) {
        return null;
    }


}
