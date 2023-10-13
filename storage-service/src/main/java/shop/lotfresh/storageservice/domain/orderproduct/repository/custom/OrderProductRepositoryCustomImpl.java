package shop.lotfresh.storageservice.domain.orderproduct.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderProductRepositoryCustomImpl implements OrderProductRepositoryCustom {

    private final JPAQueryFactory query;

}