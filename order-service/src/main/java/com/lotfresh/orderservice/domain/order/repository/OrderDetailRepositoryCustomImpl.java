package com.lotfresh.orderservice.domain.order.repository;

import com.lotfresh.orderservice.domain.order.entity.OrderDetail;
import com.lotfresh.orderservice.domain.order.service.response.BestProductsResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lotfresh.orderservice.domain.order.entity.QOrderDetail.orderDetail;

public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public OrderDetailRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(Long orderId) {
        return queryFactory.selectFrom(orderDetail)
                .where(
                        orderDetail.order.id.eq(orderId),
                        orderDetail.isDeleted.isFalse()
                )
                .fetch();
    }

    @Override
    public List<BestProductsResponse> mostSoldProducts(int limitCnt) {
        return queryFactory.select(Projections.constructor(BestProductsResponse.class,
                orderDetail.productId,
                orderDetail.id.count())
                )
                .from(orderDetail)
                .where(
                        orderDetail.isDeleted.isFalse()
                )
                .groupBy(orderDetail.productId)
                .orderBy(orderDetail.id.count().desc())
                .limit(limitCnt)
                .fetch();

    }

}
