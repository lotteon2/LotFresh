package com.lotfresh.productservice.domain.discount.repository.custom;

import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.lotfresh.productservice.domain.discount.entity.QDiscount.discount;

@RequiredArgsConstructor
public class DiscountRepositoryImpl implements DiscountRepositoryCustom {
  private final JPAQueryFactory query;

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
}
