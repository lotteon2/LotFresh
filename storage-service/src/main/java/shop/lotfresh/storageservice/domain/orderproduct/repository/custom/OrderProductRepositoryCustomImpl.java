package shop.lotfresh.storageservice.domain.orderproduct.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import shop.lotfresh.storageservice.domain.storageproduct.entity.StorageProduct;

import java.util.List;

@RequiredArgsConstructor
public class OrderProductRepositoryCustomImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public List<StorageProduct> orderProduct(Long storageId, Long productId, Long orderId, Long quantity) {
        return null;
    }
}