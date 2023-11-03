package shop.lotfresh.storageservice.domain.storageproduct.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.lotfresh.storageservice.domain.storageproduct.api.request.StorageProductSearchRequest;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;
import shop.lotfresh.storageservice.domain.storageproduct.vo.StorageStockVo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static shop.lotfresh.storageservice.domain.storage.entity.QStorage.storage;
import static shop.lotfresh.storageservice.domain.storageproduct.entity.QStorageProduct.storageProduct;


@RequiredArgsConstructor
public class StorageProductRepositoryCustomImpl implements StorageProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;



    // .and(storageProduct.expirationDateEnd.goe(twoDaysBefore)))
    // .and(storageProduct.expirationDateEnd.gt(twoDaysLater)))


    //TODO
    // 유통기한 임박상품 창고별 물품 리스트 반환
    // 특정 상품 재고 차감 + 플러스 /url/storageId/storageProductId/stock( 마감 임박 상품은 따로 처리해야해서 필요 및 보상 패턴에 필요)

    //창고별 물품 리스트 반환(마감임박 제외)
    @Override
    public List<StorageProduct> findProductsByStorageId(String province) {
        LocalDateTime twoDaysLater = LocalDateTime.now().plusDays(2);
        Date sfficientExpiry = Timestamp.valueOf(twoDaysLater);

        return queryFactory
        .selectFrom(storageProduct)
                .join(storage).on(storageProduct.storageId.eq(storage.id))
                .where(storage.province.eq(province))
                .fetch();
    }



    //특정 창고의 물품의 재고 반환(마감임박 제외)
    @Override
    public Integer getProductStock(String province, Long productId) {
        LocalDateTime twoDaysLater = LocalDateTime.now().plusDays(2);
        Date sfficientExpiry = Timestamp.valueOf(twoDaysLater);
        return queryFactory.select(storageProduct.stock.sum())
                .from(storageProduct)
                .join(storage).on(storageProduct.storageId.eq(storage.id))
                .where(storage.province.eq(province)
                        .and(storageProduct.productId.eq(productId))
                        .and(storageProduct.expirationDateEnd.goe(sfficientExpiry)))
                .fetchOne();
    }

    // 특정 창고의 물품의 리스트 반환 (마감임박 제외 주문용)
    @Override
    public List<StorageProduct> getProductOrderList(String province, Long productId) {
        LocalDateTime twoDaysLater = LocalDateTime.now().plusDays(2);
        Date sfficientExpiry = Timestamp.valueOf(twoDaysLater);

        return queryFactory.selectFrom(storageProduct)
                .join(storage).on(storageProduct.storageId.eq(storage.id))
                .where(storage.province.eq(province)
                        .and(storageProduct.productId.eq(productId))
                        .and(storageProduct.expirationDateEnd.goe(sfficientExpiry)))
                .fetch();
    }


    /////////////////////////////마감 임박 상품

    @Override
    public List<StorageStockVo> findSalesProductsByStorageId(Long storageId) {
        LocalDateTime twoDaysBefore = LocalDateTime.now().minusDays(2);
        Date nearExpiry = Timestamp.valueOf(twoDaysBefore);

        return queryFactory
                .select((Projections.constructor(
                        StorageStockVo.class,
                        storageProduct.productId,
                                storage.province,
                        storageProduct.stock.sum()
                )))
                .from(storageProduct)
                .join(storage).on(storage.id.eq(storageProduct.storageId))
                .where(storage.id.eq(storageId)
                        .and(storageProduct.expirationDateEnd.lt(nearExpiry)))
                .groupBy(storageProduct.productId)
                .fetch();

        /*return queryFactory
                .select(Projections.constructor(
                        StorageProductSearchRequest.class,
                        storageProduct,
                        storage.province,
                        storageProduct.stock.sum()))
                .from(storageProduct)
                .join(storage).on(storage.id.eq(storageProduct.storageId))
                .where(storage.id.eq(storageId)
                        .and(storageProduct.expirationDateEnd.lt(nearExpiry)))
                .groupBy(storageProduct.productId)
                .fetch();*/
    }

/*    @Override
    public Integer getSalesProductStock(String province, Long productId) {
        LocalDateTime twoDaysBefore = LocalDateTime.now().minusDays(2);
        Date nearExpiry = Timestamp.valueOf(twoDaysBefore);

        return queryFactory.select(storageProduct.stock.sum())
                .from(storageProduct)
                .join(storage).on(storageProduct.storageId.eq(storage.id))
                .where(storage.province.eq(province)
                        .and(storageProduct.productId.eq(productId))
                        .and(storageProduct.expirationDateEnd.lt(nearExpiry)))
                .fetchOne();
    }*/

    @Override
    public Integer getSalesProductStock(String province, Long productId) {
        LocalDateTime twoDaysBefore = LocalDateTime.now().minusDays(2);
        Date nearExpiry = Timestamp.valueOf(twoDaysBefore);

        return queryFactory.select(storageProduct.stock.sum())
                .from(storageProduct)
                .join(storage).on(storageProduct.storageId.eq(storage.id))
                .where(storage.province.eq(province)
                        .and(storageProduct.productId.eq(productId))
                        .and(storageProduct.expirationDateEnd.lt(nearExpiry)))
                .fetchOne();
    }


    @Override
    public List<StorageProduct> getSalesProductOrderList(String province, Long productId) {
        LocalDateTime twoDaysBefore = LocalDateTime.now().minusDays(2);
        Date nearExpiry = Timestamp.valueOf(twoDaysBefore);
        return queryFactory.selectFrom(storageProduct)
                .join(storage).on(storageProduct.storageId.eq(storage.id))
                .where(storage.province.eq(province)
                        .and(storageProduct.productId.eq(productId))
                        .and(storageProduct.expirationDateEnd.lt(nearExpiry)))
                .fetch();
    }

    @Override
    public List<StorageProduct> salesProductOrder(String province, Long productId, Integer stock) {
        return null;
    }


}
