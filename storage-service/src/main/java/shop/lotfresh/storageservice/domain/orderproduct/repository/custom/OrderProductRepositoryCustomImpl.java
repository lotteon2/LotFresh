package shop.lotfresh.storageservice.domain.orderproduct.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.lotfresh.storageservice.domain.orderproduct.entity.OrderProduct;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

import java.util.List;

import static shop.lotfresh.storageservice.domain.orderproduct.entity.QOrderProduct.orderProduct;

@RequiredArgsConstructor
public class OrderProductRepositoryCustomImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory query;

    private final JPAQueryFactory queryFactory;

    public List<OrderProduct> inventory(Long orderId){
        return queryFactory
                .selectFrom(orderProduct)
                .where(orderProduct.orderId.eq(orderId))
                .fetch();
    }
    @Override
    public List<StorageProduct> orderProduct(Long storageId, Long productId, Long orderId, Long quantity) {
        return null;
    }
}