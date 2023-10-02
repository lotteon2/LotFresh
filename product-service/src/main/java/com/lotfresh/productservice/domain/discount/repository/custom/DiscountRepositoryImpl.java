package com.lotfresh.productservice.domain.discount.repository.custom;

import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.lotfresh.productservice.domain.discount.entity.QDiscount.discount;

@RequiredArgsConstructor
public class DiscountRepositoryImpl implements DiscountRepositoryCustom {
  private final JPAQueryFactory query;
  private final EntityManager em;

  @Override
  public Optional<Discount> findByIdFetch(Long id) {
    return Optional.ofNullable(
        query
            .selectFrom(discount)
            .join(discount.category)
            .fetchJoin()
            .where(discount.id.eq(id))
            .fetchOne());
  }

  @Override
  public List<Discount> findAllEager() {
    EntityGraph<?> entityGraph = em.getEntityGraph("Discount.findAllEager");
    return query
        .selectFrom(discount)
        .setHint("javax.persistence.fetchgraph", entityGraph)
        .orderBy(discount.id.desc())
        .fetch();
  }
}
