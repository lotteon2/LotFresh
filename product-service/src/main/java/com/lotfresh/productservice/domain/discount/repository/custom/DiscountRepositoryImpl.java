package com.lotfresh.productservice.domain.discount.repository.custom;

import com.lotfresh.productservice.domain.discount.entity.Discount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.lotfresh.productservice.domain.discount.entity.QDiscount.discount;
import static com.querydsl.core.group.GroupBy.groupBy;

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

  @Override
  public Optional<Discount> findByCategoryId(Long categoryId) {
    return Optional.ofNullable(
        query
            .selectFrom(discount)
            .join(discount.category)
            .where(discount.category.id.eq(categoryId), discount.category.isDeleted.isFalse())
            .fetchOne());
  }

  @Override
  public Optional<Double> findRateByCategoryId(Long categoryId) {
    return Optional.ofNullable(
        query
            .select(discount.rate)
            .from(discount)
            .where(discount.category.id.eq(categoryId), discount.category.isDeleted.isFalse())
            .fetchOne());
  }

  @Override
  public List<Discount> findAllFetch() {
    return query
        .selectFrom(discount)
        .join(discount.category)
        .fetchJoin()
        .orderBy(discount.id.desc())
        .fetch();
  }

  @Override
  public Map<Long, Double> findRateGroupByCategory() {
    return query
        .from(discount)
        .where(discount.isDeleted.isFalse())
        .transform(groupBy(discount.category.id).as(discount.rate));
  }
}
