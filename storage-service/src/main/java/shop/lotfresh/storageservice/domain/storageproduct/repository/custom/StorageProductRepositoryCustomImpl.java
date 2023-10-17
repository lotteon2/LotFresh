package shop.lotfresh.storageservice.domain.storageproduct.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

import java.util.List;

import static shop.lotfresh.storageservice.domain.storageproduct.entity.QStorageProduct.storageProduct;


@RequiredArgsConstructor
public class StorageProductRepositoryCustomImpl implements StorageProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<StorageProduct> findProductsByStorageId(Long storageId) {
        return queryFactory
                .selectFrom(storageProduct)
                .where(storageProduct.storageId.eq(storageId))
                .fetch();
    }

    @Override
    public Long getProductStock(Long storageId, Long productId) {
        return queryFactory.select(storageProduct.stock.sum())
                .from(storageProduct)
                .where(storageProduct.storageId.eq(storageId)
                        .and(storageProduct.productId.eq(productId)))
                .fetchOne();
    }

    @Override
    public List<StorageProduct> getProductOrderList(Long storageId, Long productId) {
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
